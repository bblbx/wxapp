package cn.enncloud.service;

import java.util.List;
import java.util.Map;

/**
 * 功能列表处理类
 * @Description: 
 * @author: Davin
 * @create: 2019年6月11日上午11:26:19
 */
public interface FunctionService {
	
	/**
	 * 根据openID查询功能列表
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年6月11日下午1:51:19
	 * @update: 2019年6月11日下午1:51:19
	 * @param openId
	 * @return Map<String,List<Map<String,String>>>
	 */
	public Map<String,List<Map<String,String>>> queryFunctionsByOpenId(String openId);
}
