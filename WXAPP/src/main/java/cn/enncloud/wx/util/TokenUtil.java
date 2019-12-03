package cn.enncloud.wx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.enncloud.bean.Ticket;
import cn.enncloud.bean.Token;
import cn.enncloud.util.CommonUtil;

public class TokenUtil {
	private static Logger log = LoggerFactory.getLogger(TokenUtil.class);

	// 凭证获取（GET）
	private final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//充值ticket
	private final static String ticket_url_cz = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	//发票ticket
	private final static String ticket_url_fp = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	private static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		HttpsURLConnection conn = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException e) {
			log.error("连接超时" + CommonUtil.getTrace(e));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("https请求异常：" + CommonUtil.getTrace(e));
		} finally {
			try {
				// 释放资源
				if (bufferedReader != null) {
					bufferedReader.close();
					bufferedReader = null;
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
					inputStreamReader = null;
				}
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("资源关闭异常：" + CommonUtil.getTrace(e));
			}
		}
		return jsonObject;
	}

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static Token getToken(String appid, String appsecret) {
		Token token = null;
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		// 发起GET请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		try {
			if (null != jsonObject) {
				if (null != jsonObject.getString("access_token")){
					token = new Token();
					token.setAccessToken(jsonObject.getString("access_token"));
					token.setExpiresIn(jsonObject.getString("expires_in"));
				} else {
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getString("errcode"),
							jsonObject.getString("errmsg"));
				}
			} else {
				log.error("获取token失败");
			}
		} catch (Exception e) {
			token = null;
			log.error("获取token失败"+CommonUtil.getTrace(e));
		}
		return token;
	}
	/**
	 * 
	 * @Description: 从微信获取公众号用于调用微信JS接口的临时票据
	 * @author: liubaoxun
	 * @create: 2018年3月27日下午10:13:24
	 * @update: 2018年3月27日下午10:13:24
	 * @param token
	 * @param type 类型 CZ充值，FP发票
	 * @return Token
	 */
	public static Ticket getTicket(String token,String type) {
		Ticket ticket = null;
		String requestUrl = "";
		if ("CZ".equals(type)) {
			requestUrl = ticket_url_cz.replace("ACCESS_TOKEN", token);
		} else if ("FP".equals(type)) {
			requestUrl = ticket_url_fp.replace("ACCESS_TOKEN", token);
		}
		try {
			// 发起GET请求获取凭证
			JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
			if (null != jsonObject) {
				if (null != jsonObject.getString("ticket")){
					ticket = new Ticket();
					ticket.setTicket(jsonObject.getString("ticket"));
					ticket.setExpiresIn(jsonObject.getString("expires_in"));
				} else {
					// 获取token失败
					log.error("获取ticket失败 errcode:{} errmsg:{}", jsonObject.getString("errcode"),
							jsonObject.getString("errmsg"));
				}
			} else {
				log.error("获取ticket失败");
			}
		} catch (Exception e) {
			token = null;
			e.printStackTrace();
			log.error("获取ticket失败"+CommonUtil.getTrace(e));
		}
		return ticket;
	}
	
	/**
	 * 
	 * @Description: 进行md5加密
	 * @author: liubaoxun
	 * @create: 2018年10月9日下午3:52:12
	 * @update: 2018年10月9日下午3:52:12
	 * @param data
	 * @return
	 * @throws Exception String
	 */
    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是json 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static JSONObject sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer buffer =new StringBuffer();
        JSONObject jsonObject = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("content-type", "text/xml;charset=UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
            	buffer.append(line);
            }
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送 POST请求出现异常！获取新奥发票token失败"+CommonUtil.getTrace(e));
        }  finally{  //使用finally块来关闭输出流、输入流
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
                log.error("流关闭异常，获取新奥发票token失败"+CommonUtil.getTrace(ex));
            }
        }
        return jsonObject;
    }   
}
