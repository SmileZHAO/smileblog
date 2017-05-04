package com.shxzhlxrr.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shxzhlxrr.spring.data.JdbcDao;
import com.shxzhlxrr.test.SuperTest;

public class JdbcUtilTest extends SuperTest {
	@Autowired
	private JdbcDao jdbc;

	@Test
	public void testQueryList(){
		String sql = "select * from test where 1=?";
		List<Object> params = new ArrayList<Object>();
		params.add("1");
		List<Map<String, Object>> results = jdbc.queryList(sql,params);
		System.out.println(results);
	}
	@Test
	public void testInsert(){
		String sql = "insert into test (no,sex,name) values(?,?,?)";
		
		for(int i=0;i<5;i++){
			List<Object> params = new ArrayList<Object>();
			params.add("no123");
			params.add("girls"+i);
			params.add("no name");
			
			if(i==3){
				throw new RuntimeException("測試抛出異常");
			}

			Integer num = jdbc.insert(sql, params);
			
			System.out.println("插入了" + num + "數據");
		}
		
		
		
	}
	
}
