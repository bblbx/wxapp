package cn.enncloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.enncloud.dao.OperatorDao;
import cn.enncloud.service.OperatorService;

@Service
public class OperatorServiceImpl implements OperatorService {
	private static final Logger logger = LoggerFactory.getLogger(OperatorServiceImpl.class);
	
	@Autowired
	private OperatorDao operatorDao;

	@Override
	public String relateWeixinAccount2Operator(JSONObject jsonObject) {
		String msg = null;
		logger.info("绑定微信账号,传入参数:"+jsonObject);
		String openid = jsonObject.getString("openId");
		String operatorid = jsonObject.getString("operatorId");
		
		//根据openid查询微信账号是否已经与阶梯系统中用户进行过绑定操作
		String relatedOperatorId = operatorDao.queryOperatorByOpenId(openid);
		if(relatedOperatorId!=null && !relatedOperatorId.trim().equals("")){
			msg = "该微信已经与账号("+relatedOperatorId+")存在绑定关系，不能重复绑定";
		} else {
			//根据operatorid查询阶梯系统中账号是否已经绑定过微信账号
			String relatedOpenId = operatorDao.queryOpenIdByOperatorId(operatorid);
			if(relatedOpenId!=null && relatedOpenId.equals(openid)){
				msg = "当前微信账号已经与账号("+operatorid+")存在绑定关系，不能重复绑定！";
			} else {
				//保存或更新绑定关系
				msg = operatorDao.updateWeixinAccount2Operator(jsonObject);
			}
		}
		logger.info("绑定微信账号,结果:"+msg);
		return msg;
	}
}