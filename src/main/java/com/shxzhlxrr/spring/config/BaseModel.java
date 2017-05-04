package com.shxzhlxrr.spring.config;

import java.sql.Timestamp;

/**
 * model模版，存放了基本的列信息<br>
 * 
 * 抽象类，防止被实例化
 * 
 * @author zxr
 *
 */
public abstract class BaseModel {
	private String id;
	private Timestamp crt_time;
	private Timestamp mdf_time;
	private String crt_user;
	private String mdf_user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCrt_time() {
		return crt_time;
	}

	public void setCrt_time(Timestamp crt_time) {
		this.crt_time = crt_time;
	}

	public Timestamp getMdf_time() {
		return mdf_time;
	}

	public void setMdf_time(Timestamp mdf_time) {
		this.mdf_time = mdf_time;
	}

	public String getCrt_user() {
		return crt_user;
	}

	public void setCrt_user(String crt_user) {
		this.crt_user = crt_user;
	}

	public String getMdf_user() {
		return mdf_user;
	}

	public void setMdf_user(String mdf_user) {
		this.mdf_user = mdf_user;
	}

	@Override
	public String toString() {
		return "BaseModel [id=" + id + ", crt_time=" + crt_time + ", mdf_time=" + mdf_time + ", crt_user=" + crt_user
				+ ", mdf_user=" + mdf_user + "]";
	}

}
