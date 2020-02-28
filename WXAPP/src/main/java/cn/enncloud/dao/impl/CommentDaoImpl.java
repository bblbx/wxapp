package cn.enncloud.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.enncloud.dao.CommentDao;
import cn.enncloud.util.DataUtil;
import cn.enncloud.util.PropertyConstants;

@Repository
public class CommentDaoImpl implements CommentDao{
	private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);
	private SessionFactory sessionFactory; 
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Boolean saveComment(String companyID, String openid, String commentText) {
		String sql1= "SELECT min(id) id from t_comment where companyid=:companyID and openid=:openid and status in (0,1)";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql1)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("companyID", companyID);
		query.setString("openid", openid);
		List<Map<String, Object>> list1 = query.list();
		
		if(list1!=null && list1.size()>0){
			String sql ="INSERT into t_comment (ParentID,CompanyID,OpenID,CommentText) VALUES (:ParentID,:CompanyID,:OpenID,:CommentText) ";
			query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setInteger("ParentID", (Integer)list1.get(0).get("id"));
			query.setString("CompanyID", companyID);
			query.setString("OpenID", openid);
			query.setString("CommentText", commentText);
			return query.executeUpdate()>0?true:false;
		}else {
			String sql ="INSERT into t_comment (CompanyID,OpenID,CommentText) VALUES (:CompanyID,:OpenID,:CommentText) ";
			query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setString("CompanyID", companyID);
			query.setString("OpenID", openid);
			query.setString("CommentText", commentText);
			return query.executeUpdate()>0?true:false;
		}
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Boolean checkUser(String openid) {
		String sql1="select count(1) total from t_wx_user where OpenID=:OpenID";
		String sql2="select count(1) total from t_comment_black where OpenID=:OpenID";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql1)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("OpenID", openid);
		int total = Integer.parseInt(((Map)query.list().get(0)).get("total").toString()) ;
		if(total==0){
			logger.info("未找到用户信息，openid="+openid);
			return false;
		}
		query = sessionFactory.getCurrentSession().createSQLQuery(sql2)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("OpenID", openid);
		int total1 = Integer.parseInt(((Map)query.list().get(0)).get("total").toString()) ;
		if(total1>0){
			logger.info("用户在黑名单中，不能进行评论，openid="+openid);
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryCommentInfo(String companyID, int status, int page, int limit) {
		String sql1 ="select distinct ID from  t_comment where CompanyID=:CompanyID and Status=:Status and ParentID=null order by id desc limit :sl,:line";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql1)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("companyID", companyID);
		query.setInteger("status", status);
		query.setInteger("sl", (page-1)*limit);
		query.setInteger("line", limit);
		List<Map<String, Object>> list1 = query.list();
		if(list1!=null && list1.size()>0){
			StringBuffer idsb = new StringBuffer("(");
			for(int i=0;i<list1.size();i++){
				idsb.append(list1.get(i).get("ID")+"," );
			}
			String id = idsb.subSequence(0, idsb.length()-1).toString()+")";
			String sql2 = "select * from t_comment where Status=1 and (ParentID in "+id +" or ID in "+id +") order by ID ";
			query = sessionFactory.getCurrentSession().createSQLQuery(sql2)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return query.list();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryCommentInfo(int status, int lines) {
		String sql = "SELECT * from t_comment where status=:status  LIMIT :line";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setInteger("status", status);
		query.setInteger("line", lines);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Boolean updateStatus(int id, int status) {
		String sql ="update t_comment set status=:status where id=:id ";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setInteger("status", status);
		query.setInteger("id", id);
		query.executeUpdate();
		String sql1 = "select ID,ParentID,CompanyID,OpenID,Status from t_comment where id=:id ";
		query = sessionFactory.getCurrentSession().createSQLQuery(sql1)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setInteger("id", id);
		List<Map<String, Object>> list1 = query.list();
		if(list1!=null && list1.size()>0){
			//主评论没有通过
			if(DataUtil.IsNull(list1.get(0).get("ParentID")) && 
					status!=PropertyConstants.CommentStatus.passed && 
					status!=PropertyConstants.CommentStatus.waitting ){
				String sql2="SELECT min(id) id from t_comment where companyid=:CompanyID and "
						+ "openid=:OpenID and status in (0,1) and id<>:id ";
				query = sessionFactory.getCurrentSession().createSQLQuery(sql2)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				query.setInteger("id", id);
				query.setString("CompanyID", list1.get(0).get("CompanyID").toString());
				query.setString("OpenID", list1.get(0).get("OpenID").toString());
				List<Map<String, Object>> list2 = query.list();
				if(list2!=null && list2.size()>0){
					String sql3="update t_comment set ParentID=:ParentID where CompanyID=:CompanyID and OpenID=:OpenID and ID<>:ID";
					query = sessionFactory.getCurrentSession().createSQLQuery(sql3);
					query.setInteger("ID", (Integer)list2.get(0).get("id"));
					query.setString("CompanyID", list1.get(0).get("CompanyID").toString());
					query.setString("OpenID", list1.get(0).get("OpenID").toString());
					query.executeUpdate();
					String sql4="update t_comment set ParentID=null where ID=:ID";
					query = sessionFactory.getCurrentSession().createSQLQuery(sql4);
					query.setInteger("ID", (Integer)list2.get(0).get("id"));
					query.executeUpdate();
				}else {
					String sql4="update t_comment set ParentID=null where ID=:ID";
					query = sessionFactory.getCurrentSession().createSQLQuery(sql4);
					query.setInteger("ID", id);
					query.executeUpdate();
				}
			}
		}
		
		return true;
	}
	
	
}
