package cn.enncloud.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	/**
	 * @Description: 格式化异常信息，使其输出所有栈信息
	 * @author: liubaoxun
	 * @create: 2017年8月10日上午10:16:13
	 * @update: 2017年8月10日上午10:16:13
	 * @param t 异常
	 * @return String 栈信息
	 */
	public static String getTrace(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		e.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
	/**
	 * @Description: 判断输入的参数是否为null或者“”
	 * @author: liubaoxun
	 * @create: 2017年5月5日下午4:56:17
	 * @update: 2017年5月5日下午4:56:17
	 * @param source
	 * @return boolean
	 */
	public static boolean IsNullOrEmpty(Object source) {
		if (source == null || "".equals(source)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @Description: 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * @author: liubaoxun
	 * @create: 2018年3月27日上午10:34:20
	 * @update: 2018年3月27日上午10:34:20
	 * @param request
	 * @return
	 * @throws IOException String
	 */
	public final static String getIpAddress(HttpServletRequest request) throws IOException {  
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP"); 
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    }  
	
	/**
	 * 判断是否为定时作业激活服务器
	 * 由于集群原因，同步数据时会同步两遍，故执行此方法，只在一台服务器上执行同步操作
	 * 生产机为10.37.58.90
	 * 测试机为10.37.144.187
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static boolean isActiveQuartzServer() {
		boolean active = false;
		InetAddress ia = null;
		try {
			ia = ia.getLocalHost();
			String localip = ia.getHostAddress();
			logger.info("本机IP地址为："+localip+"定时任务指定IP："+PropertyConstants.TaskActivityIP);
			if( PropertyConstants.TaskActivityIP.equals(localip)){
				active = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return active;
	}
	
	/**
	 * 格式化流水号
	 * @param value
	 * @param len
	 * @return
	 */
	public static String buildSeq(String value, int length){
		String seq = "";
		int i = length;
		if(value.length() < i){
			StringBuilder sb = new StringBuilder();
			for(int j = 0; j < i - value.length(); j++){
				sb.append("0");
			}
			sb.append(value);
			seq = sb.toString();
		} else {
			seq = value;
		}
		return seq;
	}
	public static String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
