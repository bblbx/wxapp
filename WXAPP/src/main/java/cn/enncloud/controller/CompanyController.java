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

import cn.enncloud.service.CompanyService;
import cn.enncloud.util.DataUtil;

@RestController
@RequestMapping("/company")
public class CompanyController {
	private static final Logger logger = LoggerFactory.getLogger(ServletController.class);
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping("/getcompanylist")
    @ResponseBody
	public Map<String,Object> getCompanyList(HttpServletRequest request, Model model){
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("success", "false");
		res.put("msg", "没有查询到记录！");
		String grade = request.getParameter("grade");// 请求的类型信息
		String sphere = request.getParameter("sphere");// 请求的类型信息
		String openid = request.getParameter("openid");
		String page = request.getParameter("page");
		logger.info("用户"+openid+"分页查询公司列表，sphere="+sphere+"查询公司列表，grade="+grade);
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
		List<Map<String,Object>> list = companyService.getCompanySimpleInfoList(params, Integer.parseInt(page), 20);
		if(list.size()!=0){
			res.put("success", "true");
			res.put("msg", list);
		}
		return res;
	}
}
