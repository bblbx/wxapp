package cn.enncloud.service;

import java.util.List;
import java.util.Map;

import cn.enncloud.bean.SelectInfo;

public interface CommonService {
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
	/**
	 * 获取级联的选项，01地址级联
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2020年1月7日上午9:22:30
	 * @update: 2020年1月7日上午9:22:30
	 * @param type 01地址级联
	 * @return List<SelectInfo>
	 */
	List<SelectInfo> getJiLianSelectInfo(String type);
}
