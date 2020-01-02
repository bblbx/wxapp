package cn.enncloud.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.enncloud.dao.WXUserDao;
@Repository
public class WXUserDaoImpl implements WXUserDao{
	private static final Logger logger = LoggerFactory.getLogger(WXUserDaoImpl.class);
	private SessionFactory sessionFactory; 
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	public Boolean saveUserInfo(String openID) {
		String sql="INSERT into t_wx_user(OpenID) select :openID from DUAL where not exists(select 1 from t_wx_user where OpenID=:openID )";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("openID", openID);
		query.executeUpdate();
		return true;
	}

	@Override
	public Boolean saveVisitInfo(String openID, String companyID,String visitType) {
		String uid = UUID.randomUUID().toString();
		String sql = "INSERT INTO t_user_visit (ID,OpenID,CompanyID,Type)VALUES(:id,:openID,:companyID,:visitType)";
		String sqlh = "INSERT INTO t_user_visit_history (ID,OpenID,CompanyID,Type)VALUES(:id,:openID,:companyID,:visitType)";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("id", uid);
		query.setString("openID", openID);
		query.setString("companyID", companyID);
		query.setString("visitType", visitType);
		query.executeUpdate();
		
		query = sessionFactory.getCurrentSession().createSQLQuery(sqlh);
		query.setString("id", uid);
		query.setString("openID", openID);
		query.setString("companyID", companyID);
		query.setString("visitType", visitType);
		query.executeUpdate();
		
		return true;
	}

	@Override
	public Boolean deleteVisitInfo(int hours) {
		String sql="DELETE from t_user_visit where CreateDate<date_add(NOW(),interval :hou HOUR)";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("hou", "-"+hours);
		query.executeUpdate();
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getVisitDetail(Map<String, String> params, int lines) {
		String sql = "select * from t_user_visit where 1=1 mycontent order by CreateDate asc LIMIT :limit ";
		StringBuffer sb = new StringBuffer();
		for(String key:params.keySet()){
			sb.append(" and "+key+" =:"+key);
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.replace("mycontent", sb.toString()))
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setInteger("limit", lines);
		for(String key:params.keySet()){
			query.setString(key, params.get(key));
		}
		return query.list();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Boolean updateCompanyVisit(Map<String, Object> params) {
		String sql = "select count(1) total from t_user_visit v join t_wx_user u on v.OpenID=u.OpenID "
				+ "where v.type='02' and v.OpenID=:OpenID and v.CompanyID=:CompanyID and v.CreateDate>date_add(:today,interval -24 HOUR) "
				+ "and v.CreateDate<=:today ";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("OpenID", params.get("OpenID").toString());
		query.setString("CompanyID", params.get("CompanyID").toString());
		query.setString("today", params.get("CreateDate").toString());
		System.out.println(params.get("CreateDate").toString());
		int total = Integer.parseInt(((Map)query.list().get(0)).get("total").toString()) ;
		if(total ==1){
			logger.error("公司访问次数加1，"+params.toString());
			String sqlc = "update t_company_info set VisitNum=VisitNum+1 where CompanyID=:CompanyID";
			query = sessionFactory.getCurrentSession().createSQLQuery(sqlc);
			query.setString("CompanyID", params.get("CompanyID").toString());
			query.executeUpdate();
		}else if(total>1){
			logger.info("24小时内进行过访问，本次不更新,访问记录"+params.toString());
		}else if(total<1){
			logger.error("没有找到用户信息，很可能是假的openid，"+params.toString());
		}
		String sqlu = "update t_user_visit set Status='1' where ID=:ID ";
		query = sessionFactory.getCurrentSession().createSQLQuery(sqlu);
		query.setString("ID", params.get("ID").toString());
		query.executeUpdate();
		return true;
	}

}
