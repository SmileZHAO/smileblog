package com.shxzhlxrr.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.shxzhlxrr.spring.Application;
/**
 * 测试的父类，开启了事物管理
 * @author zxr
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes = Application.class)  
@WebAppConfiguration
@Transactional
public class SuperTest {
	
}
