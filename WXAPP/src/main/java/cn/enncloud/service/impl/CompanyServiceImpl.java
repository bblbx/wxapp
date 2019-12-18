package cn.enncloud.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.CompanyDao;
import cn.enncloud.service.CompanyService;
@Service
public class CompanyServiceImpl implements CompanyService{
	@Autowired
	private CompanyDao companyDao;
	@Override
	public List<Map<String, Object>> getCompanySimpleInfoList(Map<String, Object> params, int page, int limit) {
		// TODO Auto-generated method stub
		return companyDao.getCompanySimpleInfoList(params, page, limit);
	}

	@Override
	public Map<String, Object> getCompanyCompleteInfoByID(String companyid) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyCompleteInfoByID(companyid);
	}

}
