package cn.enncloud.wx.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.enncloud.util.CommonUtil;
import cn.enncloud.util.PropertyConstants;

public class SignUtil {
	private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);
    // 与接口配置信息中的Token要一致
    private static String token = PropertyConstants.Token;
    /**
     * 
     * @Description: 验证签名
     * @author: liubaoxun
     * @create: 2017年9月7日上午9:30:47
     * @update: 2017年9月7日上午9:30:47
     * @param signature
     * @param timestamp
     * @param nonce
     * @return boolean
     */
    public static boolean checkSignature(String signature, String timestamp,String nonce) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[] { token, timestamp, nonce };
        Arrays.sort(arr);
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error(CommonUtil.getTrace(e));
        }
        content = null;
        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * @Description: 将字节数组转换为十六进制字符串
     * @author: liubaoxun
     * @create: 2017年9月7日上午9:31:26
     * @update: 2017年9月7日上午9:31:26
     * @param byteArray
     * @return String
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * @Description: 将字节转换为十六进制字符串
     * @author: liubaoxun
     * @create: 2017年9月7日上午9:31:43
     * @update: 2017年9月7日上午9:31:43
     * @param mByte
     * @return String
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
    /**
     * 
     * @Description: 生成JS-SDK使用权限签名
     * @author: liubaoxun
     * @create: 2018年3月27日下午10:44:00
     * @update: 2018年3月27日下午10:44:00
     * @param data 格式为
		jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
		noncestr=Wm3WZYTPz0wzccnW
		timestamp=1414587457
		url=http://mp.weixin.qq.com?params=value
     * @return String
     */
    public static String generateTicketSignature(String data){
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将字符串进行sha1加密
            byte[] digest = md.digest(data.getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error(CommonUtil.getTrace(e));
        }
        return tmpStr;
    }
}