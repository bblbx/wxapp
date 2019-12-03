package cn.enncloud.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.FunctionDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FunctionDaoImpl implements FunctionDao {
	
	private SessionFactory sessionFactory; 
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String,List<Map<String,String>>> queryFunctionsByOpenId(String openId) {
		Map<String,List<Map<String,String>>> functionMap = new HashMap<String, List<Map<String,String>>>();
		String sql ="select function1.PageCode, function1.PageName, function1.Picture,"
				+ " function2.PageName as ParentName"
				+ " from T_Users TU with(nolock)"
				+ " inner join T_UserRoleRel URR with(nolock) on TU.UserID=URR.UserID"
				+ " inner join W_RoleFunctionRel RFR with(nolock) on RFR.RoleID=URR.RoleID"
				+ " inner join W_Function function1 with(nolock) on function1.FunctionID = RFR.FunctionID"
				+ " inner join W_Function function2 with(nolock) on function1.ParentID = function2.FunctionID"
				+ " where"
				+ " TU.OpenID=:OpenID"
				+ " and function1.Status=0"
				+ " and function1.Leaf=1"
				+ " order by function2.SortID, function1.SortID";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setString("OpenID", openId);
		List list = query.list();
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			String pageCode = (String) map.get("PageCode");	
			String pageName = (String) map.get("PageName");	
			String picture = (String) map.get("Picture");	
			String parentName = (String) map.get("ParentName");			
			
			Map<String,String> functionInfo = new HashMap<String,String>();
			functionInfo.put("PageCode", pageCode);
			functionInfo.put("PageName", pageName);
			functionInfo.put("Picture", picture);
			
			if(functionMap.containsKey(parentName)){
				List<Map<String,String>> functionList = functionMap.get(parentName);
				//判断该功能是否已经存在
				Boolean exist = false;
				for(int k =0;k<functionList.size();k++){
					Map<String,String> tempmap = functionList.get(k);
					if(tempmap.get("PageCode").equals(pageCode)){
						exist=true;
						break;
					}
				}
				if(exist){
					continue;
				}
				functionList.add(functionInfo);
			} else {
				List<Map<String,String>> functionList = new ArrayList<Map<String,String>>();
				functionList.add(functionInfo);
				functionMap.put(parentName, functionList);
			}
		}
		log.info("根据用户OpenID查询微信功能列表:"+openId + ",结果:"+functionMap);
		return functionMap;
	}
}
