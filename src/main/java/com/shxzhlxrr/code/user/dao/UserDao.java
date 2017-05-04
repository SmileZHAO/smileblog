package com.shxzhlxrr.code.user.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.code.user.model.UserModel;
import com.shxzhlxrr.spring.data.JdbcDao;
import com.shxzhlxrr.util.cla.ClassUtil;

@Repository
@Transactional
public class UserDao {
	@Autowired
	private JdbcDao jdbcDao;
	
	public UserModel getUserByName(String login_name){
		String sql = "select * from t_user where login_name = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(login_name);
		Map<String,Object> returnMap = jdbcDao.query(sql,params);
		if(returnMap.isEmpty()){
			return null;
		}
		UserModel model = ClassUtil.mapToObjFiled(returnMap, new UserModel());
		return model;
	}
	
}
