package cn.enncloud.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.enncloud.service.WXUserService;
import cn.enncloud.util.ActiveIpUtil;

/**
 * 访问用户的相关处理方式
 * @Description: 
 * @author: liubaoxun
 * @create: 2019年12月30日上午11:23:09
 */
@Component
public class WXUserTask {
	private static final Logger logger = LoggerFactory.getLogger(WXUserTask.class);
	@Autowired
	private WXUserService userService;
	/**
	 * 删除用户的访问数据
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月30日上午11:24:25
	 * @update: 2019年12月30日上午11:24:25 void
	 */
	@Scheduled(cron="0 0 1 * * ?")
	public void deleteVisitInfo(){
		if(!ActiveIpUtil.isActiveQuartzServer()){
			logger.info("删除用户的访问数据,非定时任务激活服务器！");
			return;
		}
		logger.info("开始删除用户访问系统数据");
		userService.deleteVisitInfo(48);
		logger.info("删除用户访问系统数据完成");
	}
	/**
	 * 更新用户访问公司情况
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2019年12月30日下午2:14:35
	 * @update: 2019年12月30日下午2:14:35 void
	 */
	@Scheduled(fixedDelay=5*1000*60, initialDelay=10000)
	public void updateCompanyVisit(){
		if(!ActiveIpUtil.isActiveQuartzServer()){
			logger.info("更新用户访问,非定时任务激活服务器！");
			return;
		}
		logger.info("开始更新用户访问");
		Map<String,String> params = new HashMap<String, String>();
		params.put("Type", "02");
		params.put("Status", "0");
		List<Map<String,Object>> list = userService.getVisitDetail(params, 500);
		logger.info("待处理的用户访问数:"+list.size());
		if(list == null || list.size()<1){
			logger.info("没有需要更新的访问数据");
			return ;
		}
		for(int i=0;i<list.size();i++){
			logger.info("处理用户访问："+list.get(i));
			userService.updateCompanyVisit(list.get(i));
		}
		logger.info("更新用户访问完成");
	}
	
}
