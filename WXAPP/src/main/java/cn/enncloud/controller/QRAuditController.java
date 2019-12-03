package cn.enncloud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.enncloud.service.OperatorService;
import cn.enncloud.util.PropertyUtil;
import cn.enncloud.wx.util.WXUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 扫微信二维码处理类
 * @Description: 
 * @author: Davin
 * @create: 2019年6月13日下午3:41:26
 */
@Slf4j
@RestController
@RequestMapping("/qraudit")
public class QRAuditController {
	
	@Autowired
	private OperatorService operatorService;
	
	private final static String APPID = PropertyUtil.getProperty("weixin.appid");
	private final static String APPSECRET = PropertyUtil.getProperty("weixin.appsecret");
	
	@RequestMapping("/scanQRCode")
	@ResponseBody
	public ModelAndView scanQRCode(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");// 获取二维码类型
		String time = request.getParameter("time");// 获取生成时间
		String code = request.getParameter("code");
		String param = request.getParameter("param");
		
		if("01".equals(type)){ //微信用户与阶梯系统用户绑定页面
			ModelAndView modelAndView = new ModelAndView("relate");
			String bindmsg = null;
			Long now = System.currentTimeMillis()/1000;//当前时间，秒
			Long old = Long.parseLong(time);
			if((now-old)>5*60){  //二维码有效时长5分钟
				bindmsg =  "当前二维码已经失效,请刷新二维码后重新扫描！";
			} else {
				//调用接口获取openid
				String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
				String par = "appid=" + APPID + "&secret=" + APPSECRET + "&code=" + code
						+ "&grant_type=authorization_code";
				JSONObject jsonObject = WXUtil.httpsRequest(url, "POST", par);
				log.info("用户jsonObject:" + jsonObject);
				String openid = jsonObject.getString("openid");
				log.info("扫码用户openid:"+openid);
				
				JSONObject paramJson = new JSONObject();
				paramJson.put("openId", openid);
				paramJson.put("operatorId", param);
				bindmsg = operatorService.relateWeixinAccount2Operator(paramJson);
				modelAndView.addObject("openid", openid);
			}
			log.info("绑定信息:"+bindmsg);
			modelAndView.addObject("msg", bindmsg);
			return modelAndView;
		}
		return null;
	}
}
