package cn.enncloud.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.enncloud.bean.SelectInfo;
import cn.enncloud.dao.CommonDao;
@Repository
public class CommonDaoImpl implements CommonDao{
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSelectInfo(String typeID) {
		String sql = "select `Name`,ID as `Code`,`Code` as cc  from t_select_info where `Status`=1 and Type=:Type order by cc";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("Type", typeID);
		return (List<Map<String, Object>>)query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectInfo> getJiLianSelectInfo(String type) {
		List<SelectInfo> re = new ArrayList<SelectInfo>();
		if("01".equals(type)){
			//地址级联
			String sql = "select p.ID pid,p.`Name` pname,c.ID cid,c.`Name` cname "
					+ "from t_select_info p left join t_select_info c on p.id=c.parentid "
					+ "where p.Type='04' and c.Status=1 and p.Status=1 order by p.ID;";
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = (List<Map<String, Object>>)query.list();
			String tempid="";
			SelectInfo tem = null;
			for(Map<String, Object> map:list){
				if(!tempid.equals(map.get("pid").toString())){
					if(tem!=null){
						re.add(tem);
					}
					tem = new SelectInfo(map.get("pid").toString(),map.get("pname").toString());
				}
				tempid = map.get("pid").toString();
				tem.getChildren().add(new SelectInfo(map.get("cid").toString(),map.get("cname").toString()));
			}
			re.add(tem);
		}
		return re;
	}

}
