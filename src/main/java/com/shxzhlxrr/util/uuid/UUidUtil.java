package com.shxzhlxrr.util.uuid;

import java.util.UUID;
/**
 * 生成唯一标识的工具类
 * @author zxr
 *
 */
public class UUidUtil {
	/**
	 * 生成32位的唯一标识
	 * @return
	 */
	public static String getId(){
		String uuid =  UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}
	
}
