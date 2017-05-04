package com.shxzhlxrr.util.log;

import org.apache.log4j.Logger;

/**
 * 日志打印
 * 
 * @author zxr
 *
 */
public class LogUtil {

	private static Logger log = Logger.getLogger(LogUtil.class);

	public static void debug(String info) {
		if (log.isDebugEnabled()) {
			log.debug(info+callMethodInfo());
		}
	}

	public static void info(String info) {
		if (log.isInfoEnabled()) {
			log.info(info+callMethodInfo());
		}
	}

	public static void error(String info) {
		if (log.isErrorEnabled()) {
			log.error(info+callMethodInfo());
		}
	}

	public static String callMethodInfo() {
		String str = (Thread.currentThread().getStackTrace()[3]).toString();
		return "  "+str.substring(str.indexOf("(") + 1, str.indexOf(")"));
	}

}
