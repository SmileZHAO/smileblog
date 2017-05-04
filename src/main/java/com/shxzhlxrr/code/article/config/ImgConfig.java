package com.shxzhlxrr.code.article.config;

import java.util.HashMap;
import java.util.Map;

public class ImgConfig {

	public static final Map<String, String> imgType = new HashMap<String, String>();

	static {
		imgType.put("png", "image/png");
		imgType.put("bmp", "image/bmp");
		imgType.put("jpg", "image/jpeg");
		imgType.put("jpeg", "image/jpeg");
	}

}
