package cn.enncloud.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 系统操作人员DAO
 * @Description: 
 * @author: Davin
 * @create: 2019年6月12日下午5:55:24
 */
public interface OperatorDao {
	
	/**
	 * 根据微信openid查询操作员
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年6月13日上午9:08:44
	 * @update: 2019年6月13日上午9:08:44
	 * @param openId
	 * @return String
	 */
	public String queryOperatorByOpenId(String openId);
	
	/**
	 * 根据微信openid查询操作员id
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年6月18日下午6:11:51
	 * @update: 2019年6月18日下午6:11:51
	 * @param openId
	 * @return String
	 */
	public Integer queryOperatorIdByOpenId(String openId);
	
	/**
	 * 根据操作员id(例如zhangs)查询微信openid
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年6月13日下午2:41:36
	 * @update: 2019年6月13日下午2:41:36
	 * @param operatorId
	 * @return String
	 */
	public String queryOpenIdByOperatorId(String operatorId);
	
	/**
	 * 根据操作员id(例如10)查询微信openid
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年7月1日上午8:52:56
	 * @update: 2019年7月1日上午8:52:56
	 * @param id
	 * @return String
	 */
	public String queryOpenIdByUserId(Integer id);
	
	/**
	 * 更新微信账号与阶梯系统账号绑定关系
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年6月13日下午2:40:59
	 * @update: 2019年6月13日下午2:40:59
	 * @param jsonObject
	 * @return String
	 */
	public String updateWeixinAccount2Operator(JSONObject jsonObject);
	

}
