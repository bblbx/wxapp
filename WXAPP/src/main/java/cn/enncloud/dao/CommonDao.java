package cn.enncloud.dao;

import java.util.List;
import java.util.Map;

public interface CommonDao {
	/**
	 * 通过类型获取下拉框内容
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月7日下午10:35:28
	 * @update: 2019年12月7日下午10:35:28
	 * @param typeID
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getSelectInfo(String typeID);
}
