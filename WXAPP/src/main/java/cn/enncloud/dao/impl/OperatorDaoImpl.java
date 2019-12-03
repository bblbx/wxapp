package cn.enncloud.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.enncloud.dao.OperatorDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OperatorDaoImpl implements OperatorDao {
	
	private SessionFactory sessionFactory; 
	
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String queryOperatorByOpenId(String openId) {
		String userNames = "";
		String sql ="select UserName"
				+ " from T_Users TU with(nolock)"
				+ " where"
				+ " TU.OpenID=:OpenID";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("OpenID", openId);
		List list = query.list();
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			String userName = (String) map.get("UserName");	
			if(i==0){
				userNames += userName;
			}else {
				userNames += "," + userName;
			}
		}
		log.info("根据用户OpenID:"+openId + ",查询用户:"+userNames);
		return userNames;
	}

	@Override
	public String queryOpenIdByOperatorId(String operatorId) {
		String sql ="select OpenID"
				+ " from T_Users TU with(nolock)"
				+ " where"
				+ " TU.UserName=:UserName";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("OpenID", StandardBasicTypes.STRING);
		query.setString("UserName", operatorId);
		String openId = (String) query.uniqueResult();
		log.info("根据用户:"+operatorId + ",查询微信OpenId:"+openId);
		return openId;
	}
	
	@Override
	public String queryOpenIdByUserId(Integer id) {
		String sql ="select OpenID"
				+ " from T_Users TU with(nolock)"
				+ " where"
				+ " TU.UserID=:UserID";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("OpenID", StandardBasicTypes.STRING);
		query.setInteger("UserID", id);
		String openId = (String) query.uniqueResult();
		log.info("根据用户:"+id + ",查询微信OpenId:"+openId);
		return openId;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Integer queryOperatorIdByOpenId(String openId) {
		Integer userID = null;
		String sql ="select UserID"
				+ " from T_Users TU with(nolock)"
				+ " where"
				+ " TU.OpenID=:OpenID";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("OpenID", openId);
		List list = query.list();
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			userID = (Integer) map.get("UserID");	
		}
		log.info("根据用户OpenID:"+openId + ",查询用户ID:"+userID);
		return userID;
	}
	
	@Override
	public String updateWeixinAccount2Operator(JSONObject jsonObject) {
		String msg = null;
		String operatorId = jsonObject.getString("operatorId");
		String openId = jsonObject.getString("openId");
		String sql ="update T_Users set OpenID=:OpenID "
				+ " where"
				+ " UserName=:UserName";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setString("OpenID", openId);
		query.setString("UserName", operatorId);
		int extresult = query.executeUpdate();
		sessionFactory.getCurrentSession().flush();
		log.info("更新微信绑定信息("+jsonObject+"),结果:"+extresult);
		if(extresult>0){
			msg = "该微信与账号("+operatorId+")绑定成功。";
		} else {
			msg = "该微信与账号("+operatorId+")绑定失败。";
		}
		return msg;
	}
}
