package cn.enncloud.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveIpUtil {
    private static final Logger logger= LoggerFactory.getLogger(ActiveIpUtil.class);
    /**
     * 判断是否为定时作业激活服务器
     * 由于集群原因，同步数据时会同步两遍，故执行此方法，只在一台服务器上执行同步操作
     * 生产机为10.37.58.90
     * 测试机为10.37.144.187
     * @return
     */
    public static boolean isActiveQuartzServer() {
        boolean active = false;
     //   InetAddress ia = null;
        try {
        //    ia = ia.getLocalHost();
            String localip = ActiveIpUtil.getLocalIP();
            logger.info("本机IP地址为："+localip+"定时任务指定IP："+PropertyConstants.TaskActivityIP);
            if( PropertyConstants.TaskActivityIP.equals(localip)){
                active = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("是定任务报错",e);
        }
        return active;
    }
    
    /**
     * 获取本地IP地址
     *
     * @throws Exception
     */
    public static String getLocalIP() throws Exception {
        if (isWindowsOS()) {
            return InetAddress.getLocalHost().getHostAddress();
        } else {
            return getLinuxLocalIp();
        }
    }

    /**
     * 判断操作系统是否是Windows
     *
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    /**
     * 获取本地Host名称
     */
    public static String getLocalHostName() throws Exception {
        return InetAddress.getLocalHost().getHostName();
    }

    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws Exception
     */
    private static String getLinuxLocalIp() throws Exception {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                                System.out.println(ipaddress);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        	logger.error("获取ip地址异常",ex);

           // System.out.println();
            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        // System.out.println("IP:"+ip);
        logger.info("IP:"+ip);
        return ip;
    }
}
