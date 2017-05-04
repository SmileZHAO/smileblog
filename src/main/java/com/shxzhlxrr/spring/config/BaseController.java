package com.shxzhlxrr.spring.config;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.util.json.JsonUtil;

@Transactional
public class BaseController {

	public static void returnMsg(HttpServletResponse res,ReturnModel model){
		res.setContentType("text/html;charset=utf-8");
		try {
			res.getWriter().println(JsonUtil.objToJson(model));
		} catch (Exception e) {
			try {
				res.getWriter().println(JsonUtil.objToJson(
						new ReturnModel(false, "系统发生错误，请联系管理员")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void returnHtml(HttpServletResponse res, String html) {
		res.setContentType("text/html;charset=utf-8");
		try {
			res.getWriter().println(html);
		} catch (Exception e) {
			try {
				res.getWriter().println(JsonUtil.objToJson(new ReturnModel(false, "系统发生错误，请联系管理员")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
