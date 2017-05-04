package com.shxzhlxrr.code.user.service;

import java.util.Map;

import com.shxzhlxrr.code.user.model.ReturnModel;

public interface IUserService {

	
	public ReturnModel login(Map<String,String> param);
	
	
}
