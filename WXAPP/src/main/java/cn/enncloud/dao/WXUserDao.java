package cn.enncloud.dao;

import java.util.List;
import java.util.Map;

public interface WXUserDao {
	/**
	 * 
	 * @Description: 保存用户信息
	 * @author: liubaoxun
	 * @create: 2019年12月30日上午10:22:46
	 * @update: 2019年12月30日上午10:22:46
	 * @param openID
	 * @return Boolean
	 */
	Boolean saveUserInfo(String openID); 
	/**
	 * 保存用户访问信息
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月30日上午10:23:28
	 * @update: 2019年12月30日上午10:23:28
	 * @param openID
	 * @param companyID
	 * @return Boolean
	 */
	Boolean saveVisitInfo(String openID,String companyID,String visitType); 
	/**
	 * 删除指定小时数之前的用户访问数据
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月30日上午10:24:30
	 * @update: 2019年12月30日上午10:24:30
	 * @param hours
	 * @return Boolean
	 */
	Boolean deleteVisitInfo(int hours);
	/**
	 * 获取访问明细信息，
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月30日下午1:24:18
	 * @update: 2019年12月30日下午1:24:18
	 * @param params 查询参数，Status，Type，Openid，CompanyID
	 * @param lines 查询行数
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getVisitDetail(Map<String,String> params,int lines);
	
	Boolean updateCompanyVisit(Map<String,Object> params);
}
