package cn.enncloud.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.FunctionDao;
import cn.enncloud.service.FunctionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FunctionServiceImpl implements FunctionService {
	
	@Autowired
	private FunctionDao functionDao;
	
	@Override
	public Map<String,List<Map<String,String>>> queryFunctionsByOpenId(String openId) {
		log.info("根据用户OpenID查询微信功能列表:"+openId);
		return functionDao.queryFunctionsByOpenId(openId);
	}
}