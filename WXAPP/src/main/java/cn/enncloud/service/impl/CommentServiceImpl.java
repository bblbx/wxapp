package cn.enncloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.CommentDao;
import cn.enncloud.service.CommentService;
import cn.enncloud.util.PropertyConstants;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentDao commentDao;

	@Override
	public Boolean saveComment(String companyID, String openid, String commentText) {
		// TODO Auto-generated method stub
		return commentDao.saveComment(companyID, openid, commentText);
	}

	@Override
	public Boolean checkUser(String openid) {
		// TODO Auto-generated method stub
		return commentDao.checkUser(openid);
	}

	@Override
	public List<List<Map<String, Object>>> queryCommentInfo(String companyID, int page, int limit) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = commentDao.queryCommentInfo(companyID, PropertyConstants.CommentStatus.passed, page, limit);
		//openid=列表中位置
		Map<String, Integer> dataMap = new HashMap<String, Integer>();
		List<List<Map<String, Object>>> result = new ArrayList<List<Map<String,Object>>>();
		if(list!=null && list.size()>0){
			for(Map<String, Object> m: list){
				String openid=m.get("OpenID").toString();
				if(dataMap.containsKey(openid)){
					result.get(dataMap.get(openid)).add(m);
				}else {
					dataMap.put(m.get("OpenID").toString(), result.size());
					List<Map<String,Object>> temp = new ArrayList<Map<String,Object>>();
					temp.add(m);
					result.add(temp);
				}
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> queryCommentInfo(String companyID, int status, int page, int limit) {
		return commentDao.queryCommentInfo(companyID,status, page, limit);
	}

	@Override
	public List<Map<String, Object>> queryCommentInfo(int status, int lines) {
		// TODO Auto-generated method stub
		return commentDao.queryCommentInfo(status, lines);
	}

}
