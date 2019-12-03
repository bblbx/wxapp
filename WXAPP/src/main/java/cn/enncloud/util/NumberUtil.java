/**
 * 
 */
package cn.enncloud.util;

import java.math.BigDecimal;

/** 
* @author liuhao  
* @version 创建时间：2017年11月22日 上午8:35:17 
* 数字转换处理类
*/

public class NumberUtil {
	/**
	  * @version 1.0 
	  * @author enn liuhao
	  * @date 2017年11月22日
	  * @Desciption 对象转Integer
	  * @param obj
	  * @return
	  */
	public static Integer getInteger(Object obj){
		if(obj==null||"".equals(obj.toString())){
			return 0;
		}
		return Integer.valueOf(obj.toString());
	}
	
    /**
      * @version 1.0 
      * @author enn liuhao
      * @date 2017年11月22日
      * @Desciption 对象转BigDecimal
      * @param obj
      * @param n
      * @return
      */
    public static BigDecimal getBigDecimal(Object obj,int n){
    	BigDecimal num;
    	if(obj==null||"".equals(obj.toString())){
			num =new BigDecimal("0.00");
		}else{
			num=new BigDecimal(obj.toString());
		}
    	
		return num.setScale(n, BigDecimal.ROUND_HALF_UP);
    }
}
