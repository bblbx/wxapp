package cn.enncloud.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.FunctionDao;
import cn.enncloud.service.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService {
	private static final Logger logger = LoggerFactory.getLogger(FunctionServiceImpl.class);
	@Autowired
	private FunctionDao functionDao;
	
	@Override
	public Map<String,List<Map<String,String>>> queryFunctionsByOpenId(String openId) {
		logger.info("根据用户OpenID查询微信功能列表:"+openId);
		return functionDao.queryFunctionsByOpenId(openId);
	}
}