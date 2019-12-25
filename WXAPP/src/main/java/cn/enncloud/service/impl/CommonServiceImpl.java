package cn.enncloud.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.CommonDao;
import cn.enncloud.service.CommonService;
@Service
public class CommonServiceImpl implements CommonService{
	@Autowired
	private CommonDao commonDao;

	@Override
	public List<Map<String, Object>> getSelectInfo(String typeID) {
		// TODO Auto-generated method stub
		return commonDao.getSelectInfo(typeID);
	}
}
