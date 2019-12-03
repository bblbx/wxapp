package cn.enncloud.dao;

import java.util.List;

/**
 * 公共服务查询DAO
 * @Description: 
 * @author: Davin
 * @create: 2019年6月17日下午5:05:04
 */
public interface CommonDao {
	
	/**
	 * @Description: 查询站点信息
	 * @author: Davin
	 * @create: 2019年6月17日下午5:05:21
	 * @update: 2019年6月17日下午5:05:21
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
	 * 查询司机
	 * @return
	 */
	public List<?> queryDrivers();
	
	/**
	 * 查询出库
	 * @return
	 */
	public List<?> queryPlaceAreas();
}
