package cn.enncloud.service;

public interface CoreService {
	/**
	 * 
	 * @Description: 处理微信发来的xml信息
	 * @author: liubaoxun
	 * @create: 2018年10月31日上午11:27:57
	 * @update: 2018年10月31日上午11:27:57
	 * @param xmlStr 微信发送的xml数据
	 * @return String
	 */
	public String ProcessWXMsg(String xmlStr);
}
