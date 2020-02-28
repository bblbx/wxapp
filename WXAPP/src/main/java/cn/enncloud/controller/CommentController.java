package cn.enncloud.controller;

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

import cn.enncloud.service.CommentService;
import cn.enncloud.util.DataUtil;

@RestController
@RequestMapping("/comment")
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/saveComment")
	public Map<String,Object> saveComment(HttpServletRequest request,Model model){
		Map<String,Object> map = new HashMap<String, Object>();
		String companyid = request.getParameter("companyid");//
		String openid = request.getParameter("openid");
		String commentText = request.getParameter("comment");
		logger.info("保存评论：openid："+openid+",companyid:"+companyid+",commentText:"+commentText);
		if(!DataUtil.IsNull(commentText) && commentText.length()>200){
			map.put("msg", "保存失败，评论内容最多200字!");
			map.put("success", false);
		}else {
			Boolean re = commentService.saveComment(companyid, openid, commentText);
			if(re){
				map.put("msg", "保存成功，审核完后评论可见!");
				map.put("success", true);
			}else{
				map.put("msg", "保存失败，请稍后重试或者联系管理员!");
				map.put("success", false);
			}
		}
		return map;
	}
	@RequestMapping("/getCommentList")
    @ResponseBody
	public Map<String,Object> getCommentList(HttpServletRequest request, Model model){
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("success", "false");
		res.put("msg", "没有查询到记录！");
		String page = request.getParameter("page");
		String companyid = request.getParameter("companyid");//
		String openid = request.getParameter("openid");
		logger.info("查看评论：openid："+openid+",companyid:"+companyid+",页数:"+page);
		List<List<Map<String,Object>>> data = commentService.queryCommentInfo(companyid, Integer.parseInt(page), 10);
		if(data !=null ){
			res.put("success", "true");
			res.put("msg", data);
		}
		return res;
	}
	
}
