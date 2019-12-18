/**
 * 
 */
package cn.enncloud.util;
/** 
* @author liuhao  
* @version 创建时间：2017年5月5日 下午3:41:50 
* 类说明 :封装返回前台的数据用
*/
 
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getMap(Object contact, int total) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("data", (List)contact);
		modelMap.put("success", true);
		modelMap.put("total", total);

		return modelMap;
	}
	
	public static Map<String, Object> getMap(Object contact) {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("data", contact);
		modelMap.put("success", true);
		return modelMap;
	}


	public static Map<String, Object> getModelMap(String msg,boolean type) {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", type);

		return modelMap;
	}

	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @Description: 判断输入的参数是否为null或者“”
	 * @author: liubaoxun
	 * @create: 2017年5月5日下午4:56:17
	 * @update: 2017年5月5日下午4:56:17
	 * @param source
	 * @return boolean
	 */
	public static boolean IsNull(Object source) {
		if (source == null || "".equals(source)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @Description: 判断输入的参数是否为null或“”，如果是则返回传入的第二个参数，如果不是则返回第一个参数
	 * @author: liubaoxun
	 * @create: 2017年5月5日下午4:57:44
	 * @update: 2017年5月5日下午4:57:44
	 * @param source 待判断的参数
	 * @param ret 如果source为空，返回的参数
	 * @return Object
	 */
	public static Object FillNull(Object source,Object ret){
		if(IsNull(source)){
			return ret;
		} else {
			return source;
		}
	}
	
	public static String sqlFilter(String str){
		if(str==null){
			return str;
		}
		return str.replaceAll("'", "");
	}
	/**
	 * @Description: 格式化异常信息，使其输出所有栈信息
	 * @author: liubaoxun
	 * @create: 2017年8月10日上午10:16:13
	 * @update: 2017年8月10日上午10:16:13
	 * @param t 异常
	 * @return String 栈信息
	 */
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
}