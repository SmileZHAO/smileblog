package com.shxzhlxrr.code.client.service;

import javax.servlet.http.HttpServletRequest;

public interface IClientService {
	/**
	 * 记录用户的信息
	 * 
	 * @param req
	 */
	public void writeClientInfo(HttpServletRequest req);

}
