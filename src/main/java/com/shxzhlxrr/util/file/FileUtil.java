package com.shxzhlxrr.util.file;

import java.io.File;

/**
 * 
 * @author zxr <br/>
 *         2017年4月14日 下午5:36:33 <br/>
 * @description
 */
public class FileUtil {
	/**
	 * 获取文件名字的后缀
	 * 
	 * @param path
	 * @return
	 */
	public static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 获取文件名字的后缀
	 * 
	 * @param path
	 * @return
	 */
	public static String getSuffix(File file) {
		return getSuffix(file.getName());
	}

}
