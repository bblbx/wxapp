package cn.enncloud.dao;

import java.util.List;
import java.util.Map;

/**
 * 功能列表DAO
 * @Description: 
 * @author: Davin
 * @create: 2019年6月11日上午11:26:19
 */
public interface FunctionDao {
	
	/**
	 * @Description: 根据openID查询功能列表
	 * @author: Davin
	 * @create: 2019年6月11日下午1:52:07
	 * @update: 2019年6月11日下午1:52:07
	 * @param openId
	 * @return Map<String,List<Map<String,String>>>
	 */
	public Map<String,List<Map<String,String>>> queryFunctionsByOpenId(String openId);
}
