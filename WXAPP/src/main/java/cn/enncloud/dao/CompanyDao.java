package cn.enncloud.dao;

import java.util.List;
import java.util.Map;

public interface CompanyDao {
	/**
	 * 根据条件获取公司列表信息，简单信息
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月17日下午10:37:45
	 * @update: 2019年12月17日下午10:37:45
	 * @param params
	 * @param page 页数
	 * @param limit 每页行数
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getCompanySimpleInfoList(Map<String,Object> params,int page,int limit);
	/**
	 * 根据id获取公司完整信息。
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月17日下午10:41:12
	 * @update: 2019年12月17日下午10:41:12
	 * @param companyid 公司编码
	 * @return Map<String,Object>
	 */
	Map<String,Object> getCompanyCompleteInfoByID(String companyid);
}
