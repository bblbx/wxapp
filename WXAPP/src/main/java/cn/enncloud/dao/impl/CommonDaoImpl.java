package cn.enncloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.enncloud.dao.CommonDao;
@Repository
public class CommonDaoImpl implements CommonDao{
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSelectInfo(String typeID) {
		String sql = "select `Name`,ID as `Code`  from t_select_info where `Status`=1 and Type=:Type";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("Type", typeID);
		return (List<Map<String, Object>>)query.list();
	}

}
