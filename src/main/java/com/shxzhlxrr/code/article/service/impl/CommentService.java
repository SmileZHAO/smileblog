package com.shxzhlxrr.code.article.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.code.article.dao.ArticleDao;
import com.shxzhlxrr.code.article.dao.CommentDao;
import com.shxzhlxrr.code.article.model.CommentModel;
import com.shxzhlxrr.code.article.model.CommentUserModel;
import com.shxzhlxrr.code.article.service.ICommentService;
import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.spring.config.BaseService;
import com.shxzhlxrr.util.md5.Md5Util;
import com.shxzhlxrr.util.reg.RegUtil;
import com.shxzhlxrr.util.string.StringUtil;

/**
 * 
 * @author zxr <br/>
 *         2017年5月3日 下午2:20:18 <br/>
 * @description
 */
@Service
@Transactional
public class CommentService extends BaseService implements ICommentService {

	@Autowired
	private CommentDao commentDao;
	@Autowired
	private ArticleDao articleDao;

	@Override
	public ReturnModel comment(Map<String, Object> param) {
		// TODO 判断用户是否已经存在，根据用户名进行判断
		// 用户名是唯一的，如果用户名相同邮件不同时进行覆盖操作
		String msg = validateInfo(param);
		if (msg != null) {// 校验没通过，直接返回
			return new ReturnModel(false, msg);
		}

		msg = isSaveUser(param);
		if (msg == null) {
			// 保存用户信息
			CommentUserModel user = saveCommentUser(param);
			// 保存用户的评论信息
			param.put("user_id", user.getId());
		} else if ("same".equals(msg)) {
			// 什么都不做
		} else {
			return new ReturnModel(false, msg);
		}
		saveComment(param);
		// 更新评论次数
		updateCommentNum(param);
		return new ReturnModel(true, "评论发表成功，请刷新查看。");
	}

	private void updateCommentNum(Map<String, Object> param) {
		String article_id = StringUtil.getVal(param, "article_id");
		param.put("id", article_id);
		Map<String, Object> articleMap = articleDao.queryArticle(param);
		int comment_num = Integer.valueOf(StringUtil.getVal(articleMap, "comment_num"));
		comment_num++;
		articleDao.updateCommentNum(comment_num, article_id);
	}

	private String validateInfo(Map<String, Object> param) {
		if (StringUtil.isNull(param.get("user_name"))) {
			return "用户名不能为空！";
		}
		if (StringUtil.isNull(param.get("email"))) {
			return "邮箱不能为空！";
		}
		if (StringUtil.isNull(param.get("comment_content"))) {
			return "评论内容不能为空！";
		}
		if (!RegUtil.isEmail(StringUtil.getVal(param, "email"))) {
			return "请输入正确的邮箱！";
		}
		return null;
	}

	private CommentModel saveComment(Map<String, Object> param) {
		CommentModel comment = new CommentModel();
		comment.setArticle_id(StringUtil.getVal(param, "article_id"));
		comment.setUser_id(StringUtil.getVal(param, "user_id"));
		comment.setComment_content(StringUtil.getVal(param, "comment_content"));
		commentDao.saveComment(comment);
		return comment;
	}

	private CommentUserModel saveCommentUser(Map<String, Object> param) {
		CommentUserModel user = new CommentUserModel();
		user.setUser_name(StringUtil.getVal(param, "user_name"));
		user.setEmail(StringUtil.getVal(param, "email"));
		String pwd = StringUtil.getVal(param, "password");
		pwd = Md5Util.getPwdMd5(pwd);
		user.setPassword(pwd);
		commentDao.saveCommentUser(user);
		return user;
	}

	/**
	 * 判断是否保存用户
	 * 
	 * @param param
	 * @return null ,未查询到用户，尽心保存；same 用户已经存在，不保存；否则，用户已经使用
	 */
	private String isSaveUser(Map<String, Object> param) {
		Map<String, Object> userMap = commentDao.queryCommentUserByName(StringUtil.getVal(param, "user_name"));
		if (!userMap.isEmpty()) {
			String user_pwd = StringUtil.getVal(param, "password");
			user_pwd = Md5Util.getPwdMd5(user_pwd);
			if (!user_pwd.equals(userMap.get("password"))) {
				return "该用户名已经被使用，请重新输入！";
			} else {
				param.put("user_id", userMap.get("id"));
				return "same";
			}
		}
		return null;
	}

}
