package com.shxzhlxrr.code.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shxzhlxrr.code.user.dao.UserDao;
import com.shxzhlxrr.code.user.model.UserModel;
import com.shxzhlxrr.test.SuperTest;

public class TestUserDao extends SuperTest{

	@Autowired
	private UserDao userDao;
	
	@Test
	public void testGetUserByName(){
		UserModel user = userDao.getUserByName("11");
		System.out.println(user);
	}
	
	
}
