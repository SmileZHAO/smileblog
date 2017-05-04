package com.shxzhlxrr.util.reg;

import org.junit.Test;

public class RegUtilTest {

	@Test
	public void isEmailTest() {
		String[] emails = { "aaa@", "aa.b@qq.com", "1123@163.com", "113fe$@11.com", "han. @sohu.com.cn",
				"han.c@sohu.com.cn.cm.cm" };
		for (String email : emails) {
			boolean flag = RegUtil.isEmail(email);
			System.out.println("email:" + email + ",是否正确:" + flag);
		}
	}

}
