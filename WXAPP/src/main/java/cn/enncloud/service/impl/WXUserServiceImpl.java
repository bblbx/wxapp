package cn.enncloud.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.WXUserDao;
import cn.enncloud.service.WXUserService;
@Service
public class WXUserServiceImpl implements WXUserService{
	private static final Logger logger = LoggerFactory.getLogger(WXUserServiceImpl.class);
	@Autowired
	private WXUserDao userDao;
	@Override
	public Boolean saveUserInfo(String openID) {
		logger.info("保存用户openid"+openID);
		return userDao.saveUserInfo(openID);
	}

	@Override
	public Boolean saveVisitInfo(String openID, String companyID,String visitType) {
		logger.info("保存用户访问情况，openid="+openID+",companyID="+companyID+",visitType="+visitType);
		return userDao.saveVisitInfo(openID, companyID,visitType);
	}

	@Override
	public Boolean deleteVisitInfo(int hours) {
		logger.info("删除历史访问信息，"+hours);
		return userDao.deleteVisitInfo(hours);
	}

	@Override
	public List<Map<String, Object>> getVisitDetail(Map<String, String> params, int lines) {
		// TODO Auto-generated method stub
		return userDao.getVisitDetail(params, lines);
	}

	@Override
	public Boolean updateCompanyVisit(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return userDao.updateCompanyVisit(params);
	}

}
