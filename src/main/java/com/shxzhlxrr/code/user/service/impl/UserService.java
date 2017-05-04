package com.shxzhlxrr.code.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shxzhlxrr.code.user.dao.UserDao;
import com.shxzhlxrr.code.user.model.ReturnModel;
import com.shxzhlxrr.code.user.model.UserModel;
import com.shxzhlxrr.code.user.service.IUserService;
import com.shxzhlxrr.spring.config.BaseService;
import com.shxzhlxrr.util.md5.Md5Util;
/**
 * 用户服务层
 * @author zxr
 *
 */
@Service
public class UserService extends BaseService implements IUserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public ReturnModel login(Map<String, String> param) {
		ReturnModel returnModel = new ReturnModel();
		String login_name = param.get("login_name");
		String pwd = param.get("pwd");
		
		pwd = Md5Util.getPwdMd5(pwd);
		
		UserModel user = userDao.getUserByName(login_name);
		
		if(user==null){
			returnModel.setMsg("用户名不存在或者密码错误");
			returnModel.setFlag(false);
			return returnModel;
		}
		if(!pwd.equals(user.getPwd())){//密码错误
			returnModel.setMsg("用户名不存在或者密码错误");
			returnModel.setFlag(false);
		}else{
			returnModel.setFlag(true);
			returnModel.setReturnObj(user);
		}
		
		return returnModel;
	}

}
