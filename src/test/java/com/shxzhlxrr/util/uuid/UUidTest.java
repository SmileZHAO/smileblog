package com.shxzhlxrr.util.uuid;

import org.junit.Test;

public class UUidTest {
	@Test
	public void getId(){
		String uuid = UUidUtil.getId();
		System.out.println(uuid);
		System.out.println(uuid.length());
	}

}
