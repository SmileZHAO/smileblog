package com.shxzhlxrr.spring.context;

import com.shxzhlxrr.code.user.model.UserModel;

public class ThreadLocalContext {

	private static final ThreadLocal<UserModel> local = new ThreadLocal<UserModel>();
	
	public static UserModel getCurUser(){
		return local.get();
	}
	
	public static void setCurUser(UserModel user){
		local.set(user);
	}
	
}
