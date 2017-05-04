package com.shxzhlxrr.spring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shxzhlxrr.code.user.model.UserModel;
import com.shxzhlxrr.spring.context.ThreadLocalContext;

/**
 * 过滤连接请求
 * 
 * @author zxr
 *
 */
@WebFilter(filterName="loginFilter",urlPatterns="/*")
public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest)request;
			String url = req.getRequestURI();
			// 对于登录网页和登录请求不做处理，还有就是js和css
			if (url.endsWith("login.html") || url.endsWith(".css") || url.endsWith(".js") || url.endsWith("login.do")
					|| "/".equals(url) || url.endsWith(".ico") || url.startsWith("/article")
					|| url.startsWith("/component")) {
				chain.doFilter(request, response);
			} else if (req.getSession().getAttribute("cur_user") == null) {// 跳转到登录首页
				req.getSession().invalidate();// 销毁session
				((HttpServletResponse)response).sendRedirect("login.html");
				ThreadLocalContext.setCurUser(null);
				return;
			}else{
				if (url.endsWith(".do")) {// 方便取当前用户信息
					ThreadLocalContext.setCurUser((UserModel) req.getSession().getAttribute("cur_user"));
				}
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		
	}
	
//	private void console(String msg){
//		System.out.println(msg);
//	}

}
