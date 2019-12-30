package cn.enncloud.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.enncloud.service.CoreService;
import cn.enncloud.wx.util.MessageUtil;

@Service
public class CoreServiceImpl implements CoreService {
	private static final Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);
	/**
	 * 
	 * @Description: 处理微信发送的消息
	 * @author: liubaoxun
	 * @create: 2018年10月31日上午10:55:29
	 * @update: 2018年10月31日上午10:55:29
	 * @param xmlStr
	 * @return String
	 */
	@Override
	public String ProcessWXMsg(String xmlStr){
		Map<String, String> xmlMap = MessageUtil.xmlToMap(xmlStr);
		if (xmlMap==null ){
			return null;
		}
		//输出数据
		for (Map.Entry<String,String> entry : xmlMap.entrySet()) {  
			System.out.println(entry.getKey() + " = " + entry.getValue());  
			logger.info(entry.getKey() + " = " + entry.getValue());
		}
		
		return null;
	} 
}
