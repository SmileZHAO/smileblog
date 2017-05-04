package com.shxzhlxrr.spring.filter;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听session
 * 
 * @author zxr <br/>
 *         2017年4月27日 下午2:39:14 <br/>
 * @description
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		int num = se.getSession().getMaxInactiveInterval();
		System.out.println("session 创建" + se.getSession().getId() + "," + num);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("session 销毁" + se.getSession().getId());
	}

}
