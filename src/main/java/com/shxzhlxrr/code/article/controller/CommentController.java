package com.shxzhlxrr.code.article.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shxzhlxrr.code.article.service.ICommentService;
import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.spring.config.BaseController;

/**
 * 
 * @author zxr <br/>
 *         2017年5月3日 下午2:15:27 <br/>
 * @description
 */
@Controller
public class CommentController extends BaseController {

	@Autowired
	private ICommentService commentService;

	@RequestMapping("/article/comment.do")
	public void uploadImg(HttpServletResponse res, HttpServletRequest req) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_name", req.getParameter("user_name"));
		param.put("email", req.getParameter("user_email"));
		param.put("password", req.getParameter("user_pwd"));
		param.put("comment_content", req.getParameter("user_comment"));
		param.put("article_id", req.getParameter("article_id"));
		try {
			ReturnModel model = commentService.comment(param);
			returnMsg(res, model);
		} catch (Exception e) {
			returnMsg(res, new ReturnModel(false, "发布评论失败！"));
			e.printStackTrace();
		}

	}

}
