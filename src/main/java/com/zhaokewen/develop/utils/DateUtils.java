package com.zhaokewen.develop.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	
	public final static String DATE_FORMAT = "yyyy-MM-dd";

	public final static String DATE_FORMAT_CN = "yyyy年MM月dd日";

	public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";

	public final static String MONTH_FORMAT = "yyyy-MM";

	public final static String DAY_FORMAT = "yyyyMMdd";

	public final static String DAY_FORMAT_SHORT = "yyMMdd";

	public final static String LONG_FORMAT = "yyyyMMddHHmmss";
	
	private final static String DATE_TYPE_YEAR = "year";
	private final static String DATE_TYPE_MONTH = "month";
	private final static String DATE_TYPE_DAY = "day";
	
	
	public static Date getCurrDateTime() {
		return new Date();
	}

	/**
	 * 取得当前系统日期，返回java.util.Date类型
	 * @return
	 */
	public static Date getCurrDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 取得当前系统时间戳
	 *
	 * @see java.sql.Timestamp
	 * @return java.sql.Timestamp 系统时间戳
	 */
	public static java.sql.Timestamp getCurrTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	
	
	/**
	 * 
	 * @Title: stringToDate
	 * @Description:  字符串解析成日期,默认  yyyy-MM-dd 类型   
	 * @author YangHui
	 * @date 2018年6月12日-上午10:47:52
	 * @param currDate
	 * @return Date
	 */
	public static Date stringToDate(String currDate){
		return stringToDate(currDate, DATE_FORMAT);
	}

	/**
	 * 
	 * @Title: stringToDate
	 * @Description: 以 format 格式将字符串解析成日期      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:46:20
	 * @param currDate
	 * @param format 格式化方式 如果 format 为空，则默认为 yyyy-MM-dd 格式
	 * @return Date
	 */
	public static Date stringToDate(String currDate, String format){
		SimpleDateFormat dtFormatdB = null;
		if(StringUtils.isBlank(format)){
			format = DATE_FORMAT;
		}
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: dateToString
	 * @Description:  日期格式化成字符串， 默认  yyyy-MM-dd 类型     
	 * @author YangHui
	 * @date 2018年6月12日-上午10:51:18
	 * @param currDate
	 * @return String
	 */
	public static String dateToString(Date currDate) {
		return dateToString(currDate, DATE_FORMAT);
	}
	
	/**
	 * 
	 * @Title: dateToString
	 * @Description: 以 format 格式将日期转成字符串      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:53:16
	 * @param currDate
	 * @param format
	 * @return String
	 */
	public static String dateToString(Date currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		if(StringUtils.isBlank(format)){
			format = DATE_FORMAT;
		}
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 
	 * @Title: addYear
	 * @Description: 对指定日期（字符串）进行年份的增加或减少（参数 month 大于零表示增加，小于零表示减少），返回处理后的日期,默认为 yyyy-MM-dd 格式      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:12:27
	 * @param dateStr 待处理的日期字符串
	 * @param year 
	 * @return String
	 */
	public static String addYear(String dateStr,Integer year){
		return addYear(dateStr,year,DATE_FORMAT);
	}
	
	/**
	 * 
	 * @Title: addYear
	 * @Description: 对指定日期（字符串）进行年份的增加或减少（参数 month 大于零表示增加，小于零表示减少），返回处理后的日期      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:12:27
	 * @param dateStr 待处理的日期字符串
	 * @param year 
	 * @param format 格式化方式 如果 format 为空，则默认为 yyyy-MM-dd 格式
	 * @return String
	 */
	public static String addYear(String dateStr,Integer year,String format){
		return addDate(dateStr,year,format,DATE_TYPE_YEAR);
	}
	
	/**
	 * 
	 * @Title: addMonth
	 * @Description: 对指定日期（字符串）进行月份的增加或减少（参数 month 大于零表示增加，小于零表示减少），返回处理后的日期,则默认为 yyyy-MM-dd 格式    
	 * @author YangHui
	 * @date 2018年6月12日-上午10:12:27
	 * @param dateStr 待处理的日期字符串
	 * @param month 
	 * @return String
	 */
	public static String addMonth(String dateStr,Integer month){
		return addMonth(dateStr,month,DATE_FORMAT);
	}

	/**
	 * 
	 * @Title: addMonth
	 * @Description: 对指定日期（字符串）进行月份的增加或减少（参数 month 大于零表示增加，小于零表示减少），返回处理后的日期      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:12:27
	 * @param dateStr 待处理的日期字符串
	 * @param month 
	 * @param format 格式化方式 如果 format 为空，则默认为 yyyy-MM-dd 格式
	 * @return String
	 */
	public static String addMonth(String dateStr,Integer month,String format){
		return addDate(dateStr,month,format,DATE_TYPE_MONTH);
	}
	
	/**
	 * 
	 * @Title: addDay
	 * @Description: 对指定日期（字符串）进行天数的增加或减少（参数 month 大于零表示增加，小于零表示减少），返回处理后的日期,则默认为 yyyy-MM-dd 格式      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:12:27
	 * @param dateStr 待处理的日期字符串
	 * @param day 
	 * @return String
	 */
	public static String addDay(String dateStr,Integer day){
		return addDay(dateStr,day,DATE_FORMAT);
	}
	
	/**
	 * 
	 * @Title: addDay
	 * @Description: 对指定日期（字符串）进行天数的增加或减少（参数 month 大于零表示增加，小于零表示减少），返回处理后的日期      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:12:27
	 * @param dateStr 待处理的日期字符串
	 * @param day 
	 * @param format 格式化方式 如果 format 为空，则默认为 yyyy-MM-dd 格式
	 * @return String
	 */
	public static String addDay(String dateStr,Integer day,String format){
		return addDate(dateStr,day,format,DATE_TYPE_DAY);
	}
	
	private static String addDate(String date,Integer plus,String format,String type){
		if(null == plus || 0 == plus){
			return date;
		}
		if(StringUtils.isBlank(format)){
			format = DATE_FORMAT;
		}
		try {
			Date curDate = stringToDate(date, format);
			Calendar cal = Calendar.getInstance();
			cal.setTime(curDate);
			if(DATE_TYPE_YEAR.equals(type)){
				cal.add(Calendar.YEAR, plus);
			}else if(DATE_TYPE_MONTH.equals(type)){
				cal.add(Calendar.MONTH, plus);
			}else if(DATE_TYPE_DAY.equals(type)){
				cal.add(Calendar.DAY_OF_MONTH, plus);
			}
			String result = dateToString(cal.getTime(), format);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 
	 * @Title: addYear
	 * @Description: 对指定日期进行年份的增加或减少（参数 year 大于零表示增加，小于零表示减少），返回处理后的日期      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:09:28
	 * @param date
	 * @param year
	 * @return Date
	 */
	public static Date addYear(Date date,Integer year){
		return addDate(date,year,DATE_TYPE_YEAR);
	}
	
	
	/**
	 * 
	 * @Title: addMonth
	 * @Description: 对指定日期进行月份的增加或减少（参数 month 大于零表示增加，小于零表示减少），返回处理后的日期      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:02:24
	 * @param date
	 * @param month
	 * @return Date
	 */
	public static Date addMonth(Date date,Integer month){
		return addDate(date,month,DATE_TYPE_MONTH);
	}
	
	/**
	 * 
	 * @Title: addDay
	 * @Description: 对指定日期进行天数的增加或减少（参数 day 大于零表示增加，小于零表示减少），返回处理后的日期      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:09:23
	 * @param date
	 * @param day
	 * @return Date
	 */
	public static Date addDay(Date date,Integer day){
		return addDate(date,day,DATE_TYPE_DAY);
	}
	
	
	/**
	 * 
	 * @Title: addDate
	 * @Description: 对指定日期进行年份、月份、天数增加或减少      
	 * @author YangHui
	 * @date 2018年6月12日-上午10:40:40
	 * @param date
	 * @param plus
	 * @param type
	 * @return Date
	 */
	private static Date addDate(Date date,Integer plus,String type){
		if(null == plus || 0 == plus){
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(DATE_TYPE_YEAR.equals(type)){
			cal.add(Calendar.YEAR, plus);
		}else if(DATE_TYPE_MONTH.equals(type)){
			cal.add(Calendar.MONTH, plus);
		}else if(DATE_TYPE_DAY.equals(type)){
			cal.add(Calendar.DAY_OF_MONTH, plus);
		}
		return cal.getTime();
	}
	
	/**
	 * 
	 * @Title: getDayOfWeek
	 * @Description: 获取当前日期为本周的第几天      
	 * @author YangHui
	 * @date 2018年6月12日-上午11:17:54
	 * @param date
	 * @return int
	 */
	public static int getDayOfWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 
	 * @Title: getWeekOfDate
	 * @Description: 判断当前日期(字符串)为星期几      
	 * @author YangHui
	 * @date 2018年6月12日-上午11:18:56
	 * @param date
	 * @return String
	 */
	public static String getWeekOfDate(String dateStr){
		Date date = stringToDate(dateStr);
		return getWeekOfDate(date);
	}
	
	/**
	 * 
	 * @Title: getWeekOfDate
	 * @Description: 判断当前日期为星期几       
	 * @author YangHui
	 * @date 2018年6月12日-上午11:26:37
	 * @param date
	 * @return String
	 */
	public static String getWeekOfDate(Date date){
		int day = getDayOfWeek(date);
		String weekDay = "";
		switch (day) {
		case 1:
			weekDay = "星期日";
			break;
		case 2:
			weekDay = "星期一";
			break;
		case 3:
			weekDay = "星期二";
			break;
		case 4:
			weekDay = "星期三";
			break;
		case 5:
			weekDay = "星期四";
			break;
		case 6:
			weekDay = "星期五";
			break;
		case 7:
			weekDay = "星期六";
			break;
		}
		return weekDay;
	}
	
	/**
	 * 
	 * @Title: getWeekOfToday
	 * @Description: 判断今天是星期几      
	 * @author YangHui
	 * @date 2018年6月12日-上午11:27:34
	 * @return
	 * @return String
	 */
	public static String getWeekOfToday(){
		Date date = new Date();
		return getWeekOfDate(date);
	}
	
	
	public static void main(String[] args) {
	}
}
