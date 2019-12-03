package cn.enncloud.util;

import java.util.Arrays;
import java.util.List;

/**
 * 常量
 */
public class PropertyConstants {
    public static String APPID = PropertyUtil.getProperty("weixin.appid");
    public static String APPSECRET = PropertyUtil.getProperty("weixin.appsecret");
    public static String DEBUG = PropertyUtil.getProperty("project.debug");//标记是否为debug模式
    public static String RepairmanRoleId = PropertyUtil.getProperty("project.repairman.roleid");
    public static String RepairOverdueHour = PropertyUtil.getProperty("project.repair.overduehour");
    public static String TaskActivityIP = PropertyUtil.getProperty("project.task.activeip");
    
    public static Boolean TEST_ENV ;
    public static List<String> TestAllowOpenId ;
    static{
    	if("true".equals(DEBUG)||"TRUE".equals(DEBUG)){
    		TEST_ENV = true;
    	} else {
    		TEST_ENV = false;
    	}
    	TestAllowOpenId = Arrays.asList(PropertyUtil.getProperty("project.TestAllowOpenId").split(","));
    }
    
    public static String TemplateMessageSendAddr = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    public static String SUCCESS = "SUCCESS";
    public static String FAIL = "FAIL";
    
    public static String NoticeTemplateMessageId = PropertyUtil.getProperty("Notice.TemplateMessageId");
    
    public static String OnlineDate=PropertyUtil.getProperty("onlineDate");
    public static String UnallowUnloadTime=PropertyUtil.getProperty("unallowUnloadTime");
    
    /**
     * 微信充值状态
     * @Description: 
     * @author: davin
     * @create: 2018年4月9日上午10:11:32
     */
	public interface RepairmanStatus {
		/**
		 * 可用
		 */
		String ENABLE = "Y";
		
		/**
		 * 不可用
		 */
		String DISABLE = "N";
	}
	
	/**
	 * 故障保修单状态
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年6月20日下午5:29:29
	 */
	public interface RepairRequestStatus {
		/**
		 * 已提交/待维修
		 */
		String YTJ = "YTJ";
		/**
		 * 维修中
		 */
		String WXZ = "WXZ";
		/**
		 * 维修完/待确认
		 */
		String DQR = "DQR";
		/**
		 * 完结
		 */
		String WJ = "WJ";
		/**
		 * 作废
		 */
		String ZF = "ZF";
		/**
		 * 驳回
		 */
		String BH = "BH";
	}
	
	public interface CallLiquidStatus{
		/**
		 * 已申请叫液
		 */
		Integer JY=0;
		
		/**
		 * 已派液
		 */
		Integer PY=1;
		
		/**
		 * 已卸液
		 */
		Integer XY=2;
		
		/**
		 * 已录入一次磅码数
		 */
		Integer BM1=3;
		
		/**
		 * 已录入额二次磅码数
		 */
		Integer BM2=4;
		
		/**
		 * 已审核
		 */
		Integer SH=5;
		/**
		 * 已经作废
		 */
		Integer ZF=-1;
	}
	
	public interface CallLiquidType{
		String JY="JY";
		
		String FY="FY";
	}

	public interface  Function{
		String PY="0102";
	}
	
	public interface MonthReportState{
		String YSC="2";//已生成
	}
}

