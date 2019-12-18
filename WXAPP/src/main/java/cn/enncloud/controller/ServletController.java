package cn.enncloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;

import cn.enncloud.service.CompanyService;
import cn.enncloud.util.DataUtil;
import cn.enncloud.util.PropertyConstants;
import cn.enncloud.wx.util.WXUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: 实现卡相关的业务，如绑定、解绑、充值
 * @author: liubaoxun
 * @create: 2018年2月13日下午10:03:54
 */
@Slf4j
@RestController
@RequestMapping("/servlet")
public class ServletController {
	private static final Logger logger = LoggerFactory.getLogger(ServletController.class);
	@Autowired
	private CompanyService companyService;
	// 根据点击公众号中菜单的不同进行不同的业务操作
	@RequestMapping("/webhome")
	@ResponseBody
	public ModelAndView webHome(HttpServletRequest request, Model model) {
		String requestpage = request.getParameter("requestpage");// 获取请求的页面
		String sphere = request.getParameter("s");// 领域
		String grade = request.getParameter("g");// 级别
		String code = request.getParameter("code");
		String openid = request.getParameter("openid");
		if (openid == null || "".equals(openid)) {
			// 调用接口获取openid
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
			String data = "appid=" + PropertyConstants.APPID + "&secret=" + PropertyConstants.APPSECRET + "&code=" + code
					+ "&grant_type=authorization_code";
			JSONObject jsonObject = WXUtil.httpsRequest(url, "POST", data);
			log.info("用户jsonObject:" + jsonObject);
			openid = jsonObject.getString("openid");
		}
		log.info("用户openid:" + openid);

		if (openid == null || openid.equals("")) {
			return new ModelAndView("weihu");
		}

		if (PropertyConstants.TEST_ENV) {
			if (!PropertyConstants.TestAllowOpenId.contains(openid)) {
				log.info("限制访问" + openid);
				return new ModelAndView("weihu");
			}
		}

		ModelAndView modelAndView = null;
		if ("other".equals(requestpage)) {
			modelAndView = new ModelAndView(new RedirectView("../servlet/otherweb"));
			modelAndView.addObject("sphere", sphere);
			modelAndView.addObject("grade", grade);
			modelAndView.addObject("openid", openid);
		} else {
			modelAndView = new ModelAndView(requestpage);
			modelAndView.addObject("openid", openid);
		}
		return modelAndView;
	}

	/**
	 * 跳转到'其他'页面主页
	 * @Description:
	 * @author: Davin
	 * @create: 2018年4月24日上午9:48:44
	 * @update: 2018年4月24日上午9:48:44
	 * @param request
	 * @param model
	 * @return ModelAndView
	 */
//	@RequestMapping("/otherMainPage")
//	@ResponseBody
//	public ModelAndView otherMainPage(HttpServletRequest request, Model model) {
//		String openid = request.getParameter("openid");
//		log.info("用户openid为" + openid);
//		
//		if (openid == null || openid.equals("")) {
//			return new ModelAndView("weihu");
//		}
//		
//		if (PropertyConstants.TEST_ENV) {
//			if (!PropertyConstants.TestAllowOpenId.contains(openid)) {
//				log.info("限制访问" + openid);
//				return new ModelAndView("weihu");
//			}
//		}
//		ModelAndView modelAndView = new ModelAndView("other");
//		modelAndView.addObject("openid", openid);
//		return modelAndView;
//	}
	
	/**
	 * @Description: 点击功能列表中显示对应的页面
	 * @author: Davin
	 * @create: 2019年6月11日下午4:46:38
	 * @update: 2019年6月11日下午4:46:38
	 * @param request
	 * @param model
	 * @return ModelAndView
	 */
	@RequestMapping("/otherweb")
	@ResponseBody
	public ModelAndView otherWeb(HttpServletRequest request, Model model) {
		String grade = request.getParameter("grade");// 请求的类型信息
		String sphere = request.getParameter("sphere");// 请求的类型信息
		String openid = request.getParameter("openid");
		ModelAndView modelAndView = new ModelAndView("ProductList");
		Map<String,Object> params = new HashMap<String, Object>();
		List<String> sp = new ArrayList<String>(),gr = new ArrayList<String>();
		if(!DataUtil.IsNull(grade)){
			gr.add(grade);
		}
		if(!DataUtil.IsNull(sphere)){
			sp.add(sphere);
		}
		params.put("sphere", sp);
		params.put("grade", gr);
		logger.info("用户"+openid+"查询公司列表，sphere="+sphere+"查询公司列表，grade="+grade);
		List<Map<String,Object>> list = companyService.getCompanySimpleInfoList(params, 1, 20);
		modelAndView.addObject("data", list);
		modelAndView.addObject("openid", openid);
		modelAndView.addObject("grade", grade);
		modelAndView.addObject("sphere", sphere);
		return modelAndView;
	}
}
