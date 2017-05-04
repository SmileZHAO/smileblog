package com.shxzhlxrr.code.article.model;

import com.shxzhlxrr.spring.config.BaseModel;

/**
 * 评论用户信息
 * 
 * @author zxr <br/>
 *         2017年5月3日 下午3:42:23 <br/>
 * @description
 */
public class CommentUserModel extends BaseModel {

	private String user_name;
	private String email;
	private String password;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "CommentUserModel [user_name=" + user_name + ", email=" + email + ", password=" + password
				+ ", toString()=" + super.toString() + "]";
	}

}
