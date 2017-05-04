package com.shxzhlxrr.util.file;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void getSuffixTest() {
		assertEquals("后缀不一致", "png", FileUtil.getSuffix("img.png"));
	}

	@Test
	public void getSuffixFileTest() {
		assertEquals("后缀不一致", "png", FileUtil.getSuffix(new File("img.png")));
	}

}
