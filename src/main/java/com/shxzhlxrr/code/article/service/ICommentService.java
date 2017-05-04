package com.shxzhlxrr.code.article.service;

import java.util.Map;

import com.shxzhlxrr.code.user.model.ReturnModel;

/**
 * 评论
 * 
 * @author zxr <br/>
 *         2017年5月3日 下午2:20:36 <br/>
 * @description
 */
public interface ICommentService {
	/**
	 * 评论
	 * 
	 * @param param
	 * @return
	 */
	public ReturnModel comment(Map<String, Object> param);

}
