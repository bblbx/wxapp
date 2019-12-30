package cn.enncloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.enncloud.service.WXUserService;
import cn.enncloud.util.PropertyConstants;

public class MyThread extends Thread{
	private static final Logger logger = LoggerFactory.getLogger(MyThread.class);
	private WXUserService userService;
	private String openid;
	public MyThread(WXUserService userService,String openid){
		this.userService = userService;
		this.openid = openid;
	}
	@Override
	public void run() {
		//保存用户信息及访问信息
		logger.info("保存用户信息及访问信息"+openid);
		userService.saveUserInfo(openid);
		userService.saveVisitInfo(openid, "",PropertyConstants.VisitType.System);
	}

}
