package cn.enncloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.CommonDao;
import cn.enncloud.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	private CommonDao CommonDao;
	
	@Override
	public List<?> queryStation() {
		return CommonDao.queryStation();
	}

	@Override
	public List<?> queryStationByOpenId(String openId) {
		return CommonDao.queryStationByOpenId(openId);
	}

	@Override
	public List<?> queryDrivers() {
	
		return CommonDao.queryDrivers();
	}

	@Override
	public List<?> queryPlaceAreas() {

		return CommonDao.queryPlaceAreas();
	}

	@Override
	public List<?> queryStationByOpenIdAndType(String openId, String bizType) {
		return CommonDao.queryStationByOpenIdAndType(openId, bizType);
	}
}
