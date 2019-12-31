package cn.enncloud.service.impl;

import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.enncloud.dao.TokenDao;
import cn.enncloud.service.WXService;
import cn.enncloud.util.HttpSender;
import cn.enncloud.util.PropertyConstants;

@Service
public class WXServiceImpl implements WXService{
	private static final Logger logger = LoggerFactory.getLogger(WXServiceImpl.class);
	@Autowired
	private TokenDao tokenDao;
	
	@Override
	public Boolean queryIsTokenTimeout(Integer minutes,String appId,String type) {
		return tokenDao.queryIsTokenTimeout(minutes, appId,type);
	}
	
	@Override
	public boolean saveToken(String accessToken, String expiresIn, String appId,String type) {
		return tokenDao.saveToken(accessToken, expiresIn, appId,type);
	}

	@Override
	public String getLatestToken(String appId,String type) {
		return tokenDao.getLatestToken(appId,type);
	}
	
	@Override
	public void sendTemplateMessage(String template_id, String openid, JSONObject msgJson) {
		String access_token = tokenDao.getLatestToken(PropertyConstants.APPID,"01");
		String url = PropertyConstants.TemplateMessageSendAddr + "?access_token="+access_token;
		
		JSONObject sendJson = new JSONObject();
		sendJson.put("touser", openid); //接收用户的OpenID
		sendJson.put("template_id", template_id); //模板消息
		
		JSONObject dataJson = new JSONObject();
		Iterator<Entry<String, Object>> entries = msgJson.entrySet().iterator();
		while(entries.hasNext()){
			Entry<String, Object> entry = entries.next();
			JSONObject obj = new JSONObject();
			obj.put("value", entry.getValue());
			//obj.put("color", "#173177");
			dataJson.put(entry.getKey(), obj);
		}
		sendJson.put("data", dataJson);
		
		logger.info("向用户发送模板消息:"+sendJson);
		try {
			String sendResult = HttpSender.getInstance().sendPostRequest(url, sendJson.toJSONString(), 6000, 8000);
			logger.info("向用户发送模板消息,返回信息:"+sendResult);
			//此处可以将微信返回结果保存到数据库中
			//JSONObject sendResultJson = JSONObject.parseObject(sendResult); 
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}
}
