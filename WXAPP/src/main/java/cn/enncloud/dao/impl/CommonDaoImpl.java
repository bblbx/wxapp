package cn.enncloud.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.CommonDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonDaoImpl implements CommonDao {
	
	private SessionFactory sessionFactory; 
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<?> queryStation() {
		String sql ="select Name, StationID from WX_StationsInfo where StationID !='' and StationID is not null";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		log.info("查询站点信息,结果:"+list);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<?> queryStationByOpenId(String openId) {
		String sql ="select WSI.Name, WSI.StationID "
				+ " from T_Users TU"
				+ " inner join W_UserStationRel WUSR on TU.UserID=WUSR.UserID"
				+ " inner join WX_StationsInfo WSI on WSI.StationID=WUSR.StationID"
				+ " where"
				+ " WSI.StationID !=''"
				+ " and WSI.StationID is not null"
				+ " and TU.OpenID= '"+openId+"'";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		log.info("根据微信用户权限查询站点信息,结果:"+list);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<?> queryDrivers() {
		StringBuffer sql=new StringBuffer("SELECT t.Name,t.Tel FROM W_CallLiquid_DispatchDriver t ");

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<?> queryPlaceAreas() {
		StringBuffer sql=new StringBuffer(" SELECT t.Guid,t.PlaceName,t.CreatePerson,t.CreateTime FROM W_CallLiquid_DispatchPlace t ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<?> queryStationByOpenIdAndType(String openId, String bizType) {
		String sql ="select WSI.Name, WSI.StationID "
				+ " from T_Users TU"
				+ " inner join W_UserStationRel WUSR on TU.UserID=WUSR.UserID"
				+ " inner join WX_StationsInfo WSI on WSI.StationID=WUSR.StationID"
				+ " where"
				+ " WSI.StationID !=''"
				+ " and WSI.StationID is not null"
				+ " and TU.OpenID= '"+openId+"'";
		if(bizType!=null && !bizType.equals("")){
			sql += " and WSI.BusinessType like '%"+bizType+"%'";
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		log.info("根据微信用户权限查询站点信息,结果:"+list);
		return list;
	}
}
