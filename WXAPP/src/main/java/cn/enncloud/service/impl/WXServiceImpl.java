package cn.enncloud.service.impl;

import java.util.Iterator;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.enncloud.dao.TokenDao;
import cn.enncloud.service.WXService;
import cn.enncloud.util.HttpSender;
import cn.enncloud.util.PropertyConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WXServiceImpl implements WXService{
	
	@Autowired
	private TokenDao tokenDao;
	
	@Override
	public Boolean queryIsTokenTimeout(Integer minutes,String appId) {
		return tokenDao.queryIsTokenTimeout(minutes, appId);
	}
	
	@Override
	public boolean saveToken(String accessToken, String expiresIn, String appId) {
		return tokenDao.saveToken(accessToken, expiresIn, appId);
	}

	@Override
	public String getLatestToken(String appId) {
		return tokenDao.getLatestToken(appId);
	}
	
	@Override
	public void sendTemplateMessage(String template_id, String openid, JSONObject msgJson) {
		String access_token = tokenDao.getLatestToken(PropertyConstants.APPID);
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
		
		log.info("向用户发送模板消息:"+sendJson);
		try {
			String sendResult = HttpSender.getInstance().sendPostRequest(url, sendJson.toJSONString(), 6000, 8000);
			log.info("向用户发送模板消息,返回信息:"+sendResult);
			//此处可以将微信返回结果保存到数据库中
			//JSONObject sendResultJson = JSONObject.parseObject(sendResult); 
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}
}
