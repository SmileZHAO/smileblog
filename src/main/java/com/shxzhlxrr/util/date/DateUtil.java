package com.shxzhlxrr.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author zxr
 * @time  2017年2月23日 下午2:07:58
 * @describe
 */
public class DateUtil {
	
	public static final String TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:dd";
	public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
	
	public static String TimeToString(Timestamp stamp ,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(stamp.getTime()));
	}
	
	public static String TimeToString(Timestamp stamp ){
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_STR);
		return sdf.format(new Date(stamp.getTime()));
	}
	
	public static String DateToString(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String DateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
		return sdf.format(date);
	}
	
	public static Date StringToDate(String date,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("字符串转换日期出错，原因："+e.getMessage());
		}
	}
	
	public static Date StringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("字符串转换日期出错，原因："+e.getMessage());
		}
	}
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date StringConvertDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			try {
				sdf = new SimpleDateFormat(TIME_FORMAT_STR);
				return sdf.parse(date);
			} catch (Exception e2) {
				throw new RuntimeException(date+"转换成日期出错");
			}
		}
	}
	/**
	 * 获取系统时间对应的字符串
	 * @param format
	 * @return
	 */
	public static String DateToString(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取系统日期
	 * @return
	 */
	public static Date sysDate(){
		return new Date();
	}
	/**
	 * 获取系统时间
	 * @return
	 */
	public static Timestamp sysTime(){
		return  new Timestamp(System.currentTimeMillis());
	}
	
	
	
}
