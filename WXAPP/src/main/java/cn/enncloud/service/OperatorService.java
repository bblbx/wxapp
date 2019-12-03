package cn.enncloud.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 系统操作人员处理类
 * @Description: 
 * @author: Davin
 * @create: 2019年6月12日下午5:29:28
 */
public interface OperatorService {
	
	/**
	 * 将微信账户信息绑定到阶梯系统账号中
	 * 规则:微信账号需要与阶梯账号一一对应
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年6月12日下午5:33:25
	 * @update: 2019年6月12日下午5:33:25
	 * @param jsonObject
	 * @return String
	 */
	public String relateWeixinAccount2Operator(JSONObject jsonObject);
}
