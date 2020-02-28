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
	 * 根据公司编码和状态获取评论信息
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2020年2月27日上午10:36:10
	 * @update: 2020年2月27日上午10:36:10
	 * @param companyID
	 * @param status
	 * @param page
	 * @param limit
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> queryCommentInfo(String companyID,int status,int page,int limit);
	/**
	 * 根据评论状态获取指定行数的信息
	 * @Description: 
	 * @author: liubaoxun
	 * @create: 2020年2月27日上午10:36:30
	 * @update: 2020年2月27日上午10:36:30
	 * @param status
	 * @param lines 获取的记录行数
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> queryCommentInfo(int status,int lines);
	
	Boolean updateStatus(int id,int status);
}
