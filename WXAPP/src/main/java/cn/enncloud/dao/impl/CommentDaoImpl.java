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

import cn.enncloud.dao.CommentDao;

@Repository
public class CommentDaoImpl implements CommentDao{
	private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);
	private SessionFactory sessionFactory; 
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	public Boolean saveComment(String companyID, String openid, String commentText) {
		String sql ="INSERT into t_comment (ID,CompanyID,OpenID,CommentText) VALUES (:ID,:CompanyID,:OpenID,:CommentText) ";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("ID", UUID.randomUUID().toString());
		query.setString("CompanyID", companyID);
		query.setString("OpenID", openid);
		query.setString("CommentText", commentText);
		return query.executeUpdate()>0?true:false;
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
	@Override
	public List<Map<String, Object>> queryCommentInfo(String companyID, int page, int limit) {
		
		return null;
	}
	
	
}
