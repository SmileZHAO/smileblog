package com.shxzhlxrr.code.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.code.user.service.IUserService;
import com.shxzhlxrr.spring.config.BaseController;

@Controller
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	/**
	 * 登录
	 */
	@RequestMapping("/login.do")
	public void login(HttpServletRequest req, HttpServletResponse res) {
		String login_name = req.getParameter("login_name");
		String pwd = req.getParameter("pwd");
		Map<String, String> param = new HashMap<String, String>();
		param.put("login_name", login_name);
		param.put("pwd", pwd);
		try {
			ReturnModel model = userService.login(param);
			if (model.isFlag() == true) {// 当校验通过的时候将用户的信息放到session中
				req.getSession().setAttribute("cur_user", model.getReturnObj());
			}
			model.setReturnObj(null);
			returnMsg(res, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/index.do")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView("index");
	}

}
