package cn.enncloud.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量
 */
@Component
public class PropertyConstants {
	/**
	 * 用户访问类型
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月30日上午11:11:59
	 */
	public interface VisitType {
		/**
		 * 访问系统
		 */
		String System = "01";
		
		/**
		 * 访问公司
		 */
		String Company = "02";
	}
	/**
	 * 允许直接访问的白名单页面
	 */
	public static String WhitePath="";
    public static String APPID = "";
    public static String Token = "";
    public static String APPSECRET = "";
    public static String TaskActivityIP = "";
    public static Boolean TEST_ENV ;//标记是否为debug模式
    public static List<String> TestAllowOpenId ;
    public static String TemplateMessageSendAddr = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    public static String SUCCESS = "SUCCESS";
    public static String FAIL = "FAIL";
    @Value("#{configProperties['project.whitePath']}")
	public void setWhitePath(String whitePath) {
		WhitePath = whitePath;
	}
    @Value("#{configProperties['weixin.appid']}")
	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}
    @Value("#{configProperties['weixin.appsecret']}")
	public void setAPPSECRET(String aPPSECRET) {
		APPSECRET = aPPSECRET;
	}
    @Value("#{configProperties['project.task.activeip']}")
	public void setTaskActivityIP(String taskActivityIP) {
		TaskActivityIP = taskActivityIP;
	}
    @Value("#{configProperties['weixin.token']}")
	public void setToken(String token) {
		Token = token;
	}
    @Value("#{configProperties['project.debug']}")
	public void setTEST_ENV(String tEST_ENV) {
    	if("true".equals(tEST_ENV)||"TRUE".equals(tEST_ENV)){
    		TEST_ENV = true;
    	} else {
    		TEST_ENV = false;
    	}
	}
    @Value("#{configProperties['project.TestAllowOpenId']}")
	public void setTestAllowOpenId(String allowOpenId) {
		TestAllowOpenId = Arrays.asList(allowOpenId.split(","));
	}
    

}

