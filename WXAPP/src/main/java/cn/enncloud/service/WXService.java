package cn.enncloud.service;

import com.alibaba.fastjson.JSONObject;

public interface WXService {
	/**
	 * @Description:  查询token是否过期
	 * @author: Davin
	 * @create: 2019年6月21日下午3:00:08
	 * @update: 2019年6月21日下午3:00:08
	 * @param minutes
	 * @param appId
	 * @return Boolean
	 */
	public Boolean queryIsTokenTimeout(Integer minutes,String appId);
	
	/**
	 * @Description: 保存Token
	 * @author: Davin
	 * @create: 2019年6月21日下午3:21:14
	 * @update: 2019年6月21日下午3:21:14
	 * @param accessToken
	 * @param expiresIn
	 * @param appId
	 * @return boolean
	 */
	public boolean saveToken(String accessToken,String expiresIn, String appId);
	
	/**
	 * @Description: 查询最新的token 
	 * @author: Davin
	 * @create: 2019年6月21日下午3:40:05
	 * @update: 2019年6月21日下午3:40:05
	 * @param appId
	 * @return String
	 */
	public String getLatestToken(String appId);
	
	/**
	 * @Description: 向用户发送模板消息
	 * @author: Davin
	 * @create: 2019年6月21日下午4:06:46
	 * @update: 2019年6月21日下午4:06:46
	 * @param template_id
	 * @param openid
	 * @param msgJson void
	 */
	public void sendTemplateMessage(String template_id, String openid, JSONObject msgJson);
}
