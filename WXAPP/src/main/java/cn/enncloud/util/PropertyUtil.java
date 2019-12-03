package cn.enncloud.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: 读取配置文件
 * @author: liubaoxun
 * @create: 2017年9月7日下午1:59:27
 */
@Slf4j
public class PropertyUtil {
	private static Properties props;
    static{
        loadProps();
    }

    synchronized static private void loadProps(){
        log.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
//　　　　　　　<!--第一种，通过类加载器进行获取properties文件流-->
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("common.properties");
//　　　　　　  <!--第二种，通过类进行获取properties文件流-->
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(new InputStreamReader(in, "utf-8"));
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
            log.error("jdbc.properties文件未找到"+CommonUtil.getTrace(e));
        } catch (IOException e) {
        	e.printStackTrace();
            log.error("出现IOException"+CommonUtil.getTrace(e));
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
            	e.printStackTrace();
                log.error("jdbc.properties文件流关闭出现异常");
            }
        }
        log.info("加载properties文件内容完成...........");
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
