package cn.enncloud.dao;

import java.util.List;
import java.util.Map;

public interface CommentDao {
	/**
	 * 保存用户评论
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2020年2月3日下午9:36:51
	 * @update: 2020年2月3日下午9:36:51
	 * @param companyID
	 * @param openid
	 * @param commentText
	 * @return Boolean
	 */
	Boolean saveComment(String companyID,String openid,String commentText);
	/**
	 * 检查用户是否可以进行评论
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2020年2月3日下午9:37:04
	 * @update: 2020年2月3日下午9:37:04
	 * @param openid
	 * @return Boolean
	 */
	Boolean checkUser(String openid);
	/**
	 * 获取评论信息
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2020年2月3日下午9:37:10
	 * @update: 2020年2月3日下午9:37:10
	 * @param companyID
	 * @param page
	 * @param limit
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> queryCommentInfo(String companyID,int page,int limit);
}
