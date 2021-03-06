package cn.enncloud.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.enncloud.bean.Ticket;
import cn.enncloud.bean.Token;
import cn.enncloud.service.WXService;
import cn.enncloud.util.ActiveIpUtil;
import cn.enncloud.util.PropertyConstants;
import cn.enncloud.wx.util.TokenUtil;

/**
 * @Description: 定时从微信获取token，并保存
 * @author: liubaoxun
 * @create: 2017年9月7日下午5:14:47
 */
@Component
public class TokenRefreshTask {
	@Autowired
	private WXService wxService;
	private static final Logger logger = LoggerFactory.getLogger(TokenRefreshTask.class);
	
	/**
	 * @Description: 更新微信token
	 * @author: liuhao
	 * @create: 2018年10月9日下午1:54:29
	 * @update: 2018年10月9日下午1:54:29 void
	 */
	@Scheduled(fixedDelay=10*1000*60, initialDelay=10000)
	public void refreshWXToken() {
		if(!ActiveIpUtil.isActiveQuartzServer()){
			logger.info("更新token,非定时任务激活服务器！");
			return;
		}
		logger.info("开始从微信获取token");
		// 统计60分钟内是否有没更新过token的公司
		boolean istimeout = wxService.queryIsTokenTimeout(-60, PropertyConstants.APPID,"01");
		if (!istimeout) {
			logger.info("微信无需重新获取token");
			return;
		}
		
		try {
			logger.info("微信获取公司token");
			// 调用接口
			Token token = TokenUtil.getToken(PropertyConstants.APPID, PropertyConstants.APPSECRET);
			if (token != null) {
				logger.info("从微信获取token" + token.getAccessToken());
				// 保存信息
				boolean res = wxService.saveToken(token.getAccessToken(), token.getExpiresIn(), PropertyConstants.APPID,"01");
				if (res) {
					logger.info("保存微信token成功");
				} else {
					logger.info("保存微信token失败");
				}
			}else{
				logger.error("微信获取公司token失败");
			}
		} catch (Exception e) {
			logger.error("微信保存公司token异常",e);
		}
	}
	//更新ticket
	@Scheduled(fixedDelay=60*1000*10 , initialDelay=10000)
	private void refreshCZTicket(){
		if(!ActiveIpUtil.isActiveQuartzServer()){
			logger.info("更新ticket,非定时任务激活服务器！");
			return;
		}
		logger.info("开始从微信获取ticket");
		// 统计60分钟内是否有没更新过token的公司
		boolean istimeout = wxService.queryIsTokenTimeout(-60, PropertyConstants.APPID,"02");
		if (!istimeout) {
			logger.info("微信无需重新获取ticket");
			return;
		}
		
		try {
			logger.info("微信获取公司ticket");
			String token= wxService.getLatestToken(PropertyConstants.APPID,"01");
		       
			if(token==null || "".equals(token)){
				logger.info("无法得到Token，获取jsapi_ticket失败");
				return ;
			}
			Ticket ticket = TokenUtil.getTicket(token,"CZ");
			logger.info("从微信获取jsapi_ticket"+ticket.getTicket());
			//保存信息
			boolean res = wxService.saveToken(ticket.getTicket(), ticket.getExpiresIn(), PropertyConstants.APPID,"02");
			if (res) {
				logger.info("保存jsapi_ticket成功");
	
			} else {
				logger.info("保存jsapi_ticket失败");
			  }
			
			
		} catch (Exception e) {
			logger.error("微信保存公司ticket异常",e);
		}
	
	}
}
