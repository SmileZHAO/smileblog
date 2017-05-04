package com.shxzhlxrr.code.user.model;

import java.sql.Timestamp;

public class UserModel {
	private String id;
	private String name;
	private String login_name;
	private String sex;
	private String pwd;
	private String mail;
	private String introduction;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", login_name=" + login_name + ", sex=" + sex + ", pwd=" + pwd
				+ ", mail=" + mail + ", introduction=" + introduction + ", crt_time=" + crt_time + ", mdf_time="
				+ mdf_time + ", crt_user=" + crt_user + ", mdf_user=" + mdf_user + "]";
	}
	
	
	
}
