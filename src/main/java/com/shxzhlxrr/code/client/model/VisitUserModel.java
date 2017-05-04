package com.shxzhlxrr.code.client.model;

import com.shxzhlxrr.spring.config.BaseModel;

public class VisitUserModel extends BaseModel {

	private String url;
	private String article_id;
	private String local_name;
	private String ip_address;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getLocal_name() {
		return local_name;
	}

	public void setLocal_name(String local_name) {
		this.local_name = local_name;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	@Override
	public String toString() {
		return "VisitUserModel [url=" + url + ", local_name=" + local_name + ", ip_address=" + ip_address + "]";
	}

}
