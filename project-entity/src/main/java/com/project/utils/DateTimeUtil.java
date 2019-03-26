package com.project.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	
	private static final ThreadLocal<SimpleDateFormat> DAYFORMAT = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	private static final ThreadLocal<SimpleDateFormat> SECONDFORMAT = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	
	
	/**
	 * 校验日期格式是否正确
	 * @param source
	 * @return
	 */
	public static boolean checkDate(String source) {
		if (source == null || "".equals(source.trim())) {
			return false;
		}
		try {
			DAYFORMAT.get().parse(source);
			return true;
		} catch (ParseException e) {
			return false;
		}finally {
			DAYFORMAT.remove();
		}
	}
	
	/**
	 * 校验时间格式是否正确
	 * @param source
	 * @return
	 */
	public static boolean checkTime(String source) {
		if (source == null || "".equals(source.trim())) {
			return false;
		}
		try {
			SECONDFORMAT.get().parse(source);
			return true;
		} catch (ParseException e) {
			return false;
		}finally {
			SECONDFORMAT.remove();
		}
	}
	
	/**
	 * 将字符串转化为日期
	 * @param source
	 * @return
	 */
	public static Date toDate(String source) {
		try {
			return DAYFORMAT.get().parse(source);
		} catch (ParseException e) {
			throw new RuntimeException();
		}finally {
			DAYFORMAT.remove();
		}
	}
	
	/**
	 * 将字符串转化为时间
	 * @param source
	 * @return
	 */
	public static Date toTime(String source) {
		try {
			return SECONDFORMAT.get().parse(source);
		} catch (ParseException e) {
			throw new RuntimeException();
		}finally {
			SECONDFORMAT.remove();
		}
	}
	/**
	 * 格式化为日期
	 * @param source
	 * @return
	 */
	public static String toDate(Date source) {
		try {
			return DAYFORMAT.get().format(source);
		}
		finally {
			DAYFORMAT.remove();
		}
	}
	
	/**
	 * 格式化为时间戳
	 * @param source
	 * @return
	 */
	public static String toTime(Date source) {
		try {
			return SECONDFORMAT.get().format(source);
		}finally {
			SECONDFORMAT.remove();
		}
	}
}
