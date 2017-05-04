package com.shxzhlxrr.spring.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shxzhlxrr.code.client.service.IClientService;
import com.shxzhlxrr.util.json.JsonUtil;
import com.shxzhlxrr.util.log.LogUtil;

/**
 * 请求参数的过滤器
 * 
 * @author zxr
 *
 */
@WebFilter(filterName = "reqFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {

	@Autowired
	private IClientService clientService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// System.out.println("初始化");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			long start_time = System.currentTimeMillis();
			HttpServletRequest req = (HttpServletRequest) request;
			String url = req.getRequestURI();
			LogUtil.info(url);
			if (clientService == null) {// 解决在tomcat下注入失败问题，因为filter初始化的时候bean还没初始化，所以注入失败
				clientService = (IClientService) WebApplicationContextUtils
						.getRequiredWebApplicationContext(req.getServletContext()).getBean("clientService");
			}
			clientService.writeClientInfo(req);// 统计页面信息
			if (!url.startsWith("/component") && !url.endsWith(".js") && !url.endsWith(".css")) {
				LogUtil.info("=========================请求开始(" + Thread.currentThread().getId() + "," + url
						+ ")========================");
				@SuppressWarnings("rawtypes")
				Map paramMap = req.getParameterMap();
				String jsonParam = JsonUtil.objToJson(paramMap);
				LogUtil.info("url:" + url);
				LogUtil.info("param:" + jsonParam);
				chain.doFilter(request, response);
				LogUtil.info("=========================请求结束(" + Thread.currentThread().getId() + "," + url
						+ ")========================");
			} else {
				chain.doFilter(request, response);
			}
			long end_time = System.currentTimeMillis();
			long time = (end_time - start_time) / 1000;
			LogUtil.info("url:" + url + ",用时（秒）:" + time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {

	}

}
