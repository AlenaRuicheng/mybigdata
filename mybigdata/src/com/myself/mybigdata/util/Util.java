package com.myself.mybigdata.util;

import java.net.Inet4Address;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {
	/**
	 * 得到的前一天的系统时间 
	 */
	public static String getYesterdaySystemDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return simpleDateFormat.format(calendar.getTime());
	}
	
	public static String getDateTime(String line) {
		return format(line, "yyyy/MM/dd HH:mm:ss");
	}
	
	public static String getDate(String line) {
		return format(line, "yyyy/MM/dd");
	}
	
	public static String getTime(String line) {
		return format(line, "HH:mm:ss");
	}
	
	public static String getYear(String line) {
		return format(line, "yyyy");
	}

	public static String getMonth(String line) {
		return format(line, "M");
	}

	public static String getDay(String line) {
		return format(line, "d");
	}

	/**
	 * 解析时间
	 */
	public static String format(String line, String pattern) {
		String[] strArr = line.split(" ");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss", Locale.US);
		try {
			Date date = simpleDateFormat.parse(strArr[3]);
			SimpleDateFormat newPattern = new SimpleDateFormat(pattern);
			return newPattern.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取主机IP 
	 */
	public static String getLocalHostIP() {
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取主机名 
	 */
	public static String getLocalHostName() {
		try {
			return Inet4Address.getLocalHost().getHostName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
