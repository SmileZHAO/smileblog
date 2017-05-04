package com.shxzhlxrr.util.string;

import java.util.Map;

public class StringUtil {
	
	public static boolean isNull(Object obj){
		if(obj==null||"".equals(obj)){
			return true;
		}
		if((obj instanceof String)&&("".equals(String.valueOf(obj).trim()))){
			return true;
		}
		return false;
	}
	
	
	public static String getVal(Map<String, Object> map, String key) {
		Object valO = map.get(key);
		if (valO == null) {
			return "";
		}
		return String.valueOf(valO).trim();
	}

	public static String firstUpper(String str) {
		char[] chs = str.toCharArray();
		if (chs[0] >= 97) {
			chs[0] -= 32;
		}
		return String.valueOf(chs);
	}

}
