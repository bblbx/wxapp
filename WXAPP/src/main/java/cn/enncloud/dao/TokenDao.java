package cn.enncloud.dao;

public interface TokenDao {
	
	/**
	 * @Description: 查询token是否过期
	 * @author: Davin
	 * @create: 2019年6月21日下午2:42:52
	 * @update: 2019年6月21日下午2:42:52
	 * @param minutes
	 * @param appId
	 * @return Boolean
	 */
	Boolean queryIsTokenTimeout(Integer minutes,String appId,String type);
	
	/**
	 * @Description: 保存Token
	 * @author: Davin
	 * @create: 2019年6月21日下午3:22:02
	 * @update: 2019年6月21日下午3:22:02
	 * @param accessToken
	 * @param expiresIn
	 * @param appId
	 * @return boolean
	 */
	public boolean saveToken(String accessToken,String expiresIn, String appId,String type);
	
	/**
	 * @Description: 查询最新token
	 * @author: Davin
	 * @create: 2019年6月21日下午3:41:08
	 * @update: 2019年6月21日下午3:41:08
	 * @param appId
	 * @return String
	 */
	public String getLatestToken(String appId,String type);
}
