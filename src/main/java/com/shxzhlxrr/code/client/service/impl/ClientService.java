package com.shxzhlxrr.code.client.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.code.client.dao.ClientDao;
import com.shxzhlxrr.code.client.model.VisitUserModel;
import com.shxzhlxrr.code.client.service.IClientService;
import com.shxzhlxrr.spring.config.BaseService;
import com.shxzhlxrr.util.sys.SystemUtils;

@Service
@Transactional
public class ClientService extends BaseService implements IClientService {
	@Autowired
	private ClientDao clientDao;

	@Override
	public void writeClientInfo(final HttpServletRequest req) {
		String url = req.getRequestURI();
		// 只有当是这两个url的时候才会进行统计，因为目前只有这两个是对外开放的
		if (!("/".equals(url) || url.startsWith("/article"))) {
			return;
		}
		// 如果session中没有该用户的信息，那么需要先记录该用户的客户端信息，之后如果是访问的文章信息，需要更新文章的访问次数
		String ip_address = SystemUtils.getIpAddr(req);
		// String local_name = SystemUtils.getHostName(ip_address);
		// String browser_info = SystemUtils.getRequestBrowserInfo(req);
		String system_info = SystemUtils.getRequestSystemInfo(req);
		HttpSession session = req.getSession();
		String client_info = ip_address + "_" + system_info;

		VisitUserModel visitUser = new VisitUserModel();
		visitUser.setUrl(url);
		visitUser.setIp_address(ip_address);
		visitUser.setLocal_name(ip_address + "_" + system_info);
		if (req.getSession().getAttribute("client_name") == null) {
			// 保存用户的客户端信息
			clientDao.insertClientInfo(visitUser);
			session.setAttribute("client_name", client_info);
		}

	}

}
