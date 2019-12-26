package cn.enncloud.dao.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.enncloud.dao.TokenDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TokenDaoImpl implements TokenDao{
	
	private SessionFactory sessionFactory; 
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Boolean queryIsTokenTimeout(Integer minutes, String appId) {
		Boolean istimeout = true;
		StringBuffer str=new StringBuffer("select count(1) as Count from t_token t ");
		str.append(" where (t.CreateDate<date_add(NOW(),interval :minutes minute) OR t.CreateDate is null) AND t.AppId=:appId");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(str.toString());
		query.setInteger("minutes", minutes);
		query.setString("appId", appId);
		Integer count = (Integer) query.addScalar("Count", StandardBasicTypes.INTEGER).uniqueResult();
		if(count==0){
			istimeout=false;
		}
		return istimeout;
	}
	
	@Override
	public boolean saveToken(String accessToken, String expiresIn, String appId) {
		if(accessToken == null || expiresIn == null){
			return false;
		}
		
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String sqlHistory = "INSERT INTO t_token_history (GUID,AccessToken,ExpiresIn,CreateDate,AppId) VALUES(:uuid,:access_token,:expires_in,NOW(),:appId);";
		String sql = "INSERT INTO t_token (GUID,AccessToken,ExpiresIn,CreateDate,AppId) VALUES(:uuid,:access_token,:expires_in,NOW(),:appId);";
		//先删除
		String sqldel ="DELETE FROM t_token WHERE AppId=:appId";
		Query query=sessionFactory.getCurrentSession().createSQLQuery(sqldel);
		query.setString("appId", appId);
		query.executeUpdate();
		//添加历史表
	    query = sessionFactory.getCurrentSession().createSQLQuery(sqlHistory);
		query.setString("uuid", uuid);
		query.setString("access_token", accessToken);
		query.setString("expires_in", expiresIn);
		query.setString("appId", appId);
		query.executeUpdate();
		//添加生效表
		query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("uuid", uuid);
		query.setString("access_token", accessToken);
		query.setString("expires_in", expiresIn);
		query.setString("appId", appId);
		int i = query.executeUpdate();
		return i>0?true:false;
	}
	
	@Override
	public String getLatestToken(String appId) {
		String sql = "select t.AccessToken from t_token t where t.AppId=:appId order by t.CreateDate desc LIMIT 1";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("appId", appId);
		String access_token = (String) query.addScalar("AccessToken", StandardBasicTypes.STRING).uniqueResult();
		log.info("根据APPID("+appId+")查询最新token:"+access_token);
		return access_token;
	}
}
