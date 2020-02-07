package cn.enncloud.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.enncloud.dao.CommentDao;
import cn.enncloud.service.CommentService;

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
	public List<Map<String, Object>> queryCommentInfo(String companyID, int page, int limit) {
		// TODO Auto-generated method stub
		return commentDao.queryCommentInfo(companyID, page, limit);
	}

}
