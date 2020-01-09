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
import org.springframework.web.util.HtmlUtils;

import cn.enncloud.service.CompanyService;
import cn.enncloud.service.WXService;
import cn.enncloud.service.WXUserService;
import cn.enncloud.util.DataUtil;
import cn.enncloud.util.PropertyConstants;
import cn.enncloud.util.WXPayUtil;
import cn.enncloud.wx.util.SignUtil;

@RestController
@RequestMapping("/company")
public class CompanyController {
	private static final Logger logger = LoggerFactory.getLogger(ServletController.class);
	@Autowired
	private CompanyService companyService;

	@Autowired
	private WXUserService userService;
	@Autowired
	private WXService wxService;
	@RequestMapping("/getcompanylist")
    @ResponseBody
	public Map<String,Object> getCompanyList(HttpServletRequest request, Model model){
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("success", "false");
		res.put("msg", "没有查询到记录！");
		String grade = request.getParameter("grade");// 请求的类型信息
		String sphere = request.getParameter("sphere");// 请求的类型信息
		String age = request.getParameter("age");// 年龄
		String county = request.getParameter("county");// 区县
		String viewOrder = request.getParameter("viewOrder");// 请求的类型信息
		String ageOrder = request.getParameter("ageOrder");// 请求的类型信息
		String openid = request.getParameter("openid");
		String page = request.getParameter("page");
		String city = request.getParameter("city");//城市
		String search = request.getParameter("search");//模糊搜索
		String ageViewOrder = request.getParameter("ageViewOrder");//优先排序字段，age/view
		String recomend = request.getParameter("recomend");//是否推荐
		
		
		Map<String,Object> params = new HashMap<String, Object>();
		List<String> sp = new ArrayList<String>(),gr = new ArrayList<String>(),ageList = new ArrayList<String>(),countyList = new ArrayList<String>();
		if(!DataUtil.IsNull(grade)){
			gr.add(grade);
		}
		if(!DataUtil.IsNull(sphere)){
			sp.add(sphere);
		}
		if(!DataUtil.IsNull(age)){
			String[] temp = age.split(",");
			for(String item:temp){
				ageList.add(item);
			}
		}
		if(!DataUtil.IsNull(county)){
			String[] temp = county.split(",");
			for(String item:temp){
				countyList.add(item);
			}
		}
		params.put("sphere", sp);
		params.put("grade", gr);
		params.put("viewOrder", viewOrder);
		params.put("ageOrder", ageOrder);
		params.put("county", countyList);
		params.put("age", ageList);
		params.put("openid", openid);
		params.put("page", page);
//		params.put("city", city);
		params.put("search", search);
		params.put("ageViewOrder", ageViewOrder);
		params.put("recomend", recomend);
		logger.info("用户"+openid+"分页查询公司列表，params="+params);
		List<Map<String,Object>> list = companyService.getCompanySimpleInfoList(params, Integer.parseInt(DataUtil.FillNull(page, "1").toString()), 20);
		if(list !=null){
			res.put("success", "true");
			res.put("msg", list);
		}
		return res;
	}
	//显示公司明细
	@SuppressWarnings("unchecked")
	@RequestMapping("/detail")
	@ResponseBody
	public ModelAndView otherWeb(HttpServletRequest request, Model model) {
		String companyid = request.getParameter("id");// 公司的id信息
		String grade = request.getParameter("grade");// 请求的类型信息
		String sphere = request.getParameter("sphere");// 请求的类型信息
		String age = request.getParameter("age");// 年龄
		String county = request.getParameter("county");// 区县
		String viewOrder = request.getParameter("viewOrder");// 请求的类型信息
		String ageOrder = request.getParameter("ageOrder");// 请求的类型信息
		String openid = request.getParameter("openid");
		String page = request.getParameter("page");
		String city = request.getParameter("city");//城市
		String search = request.getParameter("search");//模糊搜索
		String ageViewOrder = request.getParameter("ageViewOrder");//优先排序字段，age/view
		String recomend = request.getParameter("recomend");//是否推荐
		
		
		ModelAndView modelAndView = new ModelAndView("ProductDetail");
		logger.info("查询公司明细，companyid="+companyid+",grade="+grade+",sphere="+sphere+",openid="+openid);
		if(!DataUtil.IsNull(companyid)){
			//保存用户访问信息
			userService.saveVisitInfo(openid, companyid,PropertyConstants.VisitType.Company);
			Map<String, Object> map = companyService.getCompanyCompleteInfoByID(companyid);
			modelAndView.addAllObjects(map);
			List<Map<String,Object>> orglist = (List<Map<String,Object>>)map.get("rich");
			List<Map<String,Object>> richlist = new ArrayList<Map<String,Object>>();
			if(orglist!=null){
				for(Map<String,Object> item : orglist){
					item.put("Text", HtmlUtils.htmlUnescape(item.get("Text").toString()));
					richlist.add(item);
				}
			}
			modelAndView.addObject("openid", openid);
			modelAndView.addObject("grade", grade);
			modelAndView.addObject("sphere", sphere);
			modelAndView.addObject("county", county);
			modelAndView.addObject("city", city);
			modelAndView.addObject("age", age);
			modelAndView.addObject("recomend", recomend);
			modelAndView.addObject("viewOrder", viewOrder);
			modelAndView.addObject("ageOrder", ageOrder);
			modelAndView.addObject("ageViewOrder", ageViewOrder);
			modelAndView.addObject("search", search);
			modelAndView.addObject("rich", richlist);
		}
		//获取js-sdk签名
		String parms = request.getQueryString();
		String timeStamp = WXPayUtil.getCurrentTimestamp()+"";
		String nonceStr = WXPayUtil.generateNonceStr();
		Map<String,String> signMap = new HashMap<String, String>();
		signMap.put("appId", PropertyConstants.APPID);
		signMap.put("timeStamp", timeStamp);
		signMap.put("nonceStr", nonceStr);
		String ticket =wxService.getLatestToken(PropertyConstants.APPID, "02");
		StringBuffer dataBufer = new StringBuffer();
		dataBufer.append("jsapi_ticket="+ticket)
		.append("&noncestr="+nonceStr)
		.append("&timestamp="+timeStamp)
		.append("&url="+PropertyConstants.WEB_URL+"/"+PropertyConstants.WEB_PROJECT+"/company/detail?"+parms);
		String signature =  SignUtil.generateTicketSignature(dataBufer.toString());
		signMap.put("signature", signature);
		modelAndView.addObject("appId", PropertyConstants.APPID);
		modelAndView.addObject("timeStamp", timeStamp);
		modelAndView.addObject("nonceStr", nonceStr);
		modelAndView.addObject("signature", signature);
		logger.info("生成签名参数：" + dataBufer.toString()+"对应签名"+signature);
		return modelAndView;
	}
	
	
}
