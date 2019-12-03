package cn.enncloud.service;

import java.util.List;

/**
 * 公共服务服务
 * @Description: 
 * @author: Davin
 * @create: 2019年6月17日下午5:02:23
 */
public interface CommonService {
	
	/**
	 * 查询站点信息
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年6月17日下午5:01:35
	 * @update: 2019年6月17日下午5:01:35
	 * @return List<?>
	 */
	public List<?> queryStation();
	
	/**
	 * 根据微信用户权限查询站点信息
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年7月5日上午9:25:26
	 * @update: 2019年7月5日上午9:25:26
	 * @param openId
	 * @return List<?>
	 */
	public List<?> queryStationByOpenId(String openId);
	
	/**
	 * 根据微信用户权限查询站点信息，区分站点类型，比如LPG、油品
	 * @Description: 
	 * @author: Davin
	 * @create: 2019年7月5日上午9:25:26
	 * @update: 2019年7月5日上午9:25:26
	 * @param openId
	 * @return List<?>
	 */
	public List<?> queryStationByOpenIdAndType(String openId, String bizType);
	
	/**
	 * 派单查询司机
	 * @return
	 */
	public List<?> queryDrivers();
	
	/**
	 * 查询出库区
	 * @return
	 */
	public List<?> queryPlaceAreas();
}
 