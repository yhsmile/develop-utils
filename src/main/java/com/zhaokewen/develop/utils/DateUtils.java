package com.zhaokewen.develop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public final static String DATE_FORMAT = "yyyy-MM-dd";

	public final static String DATE_FORMAT_CN = "yyyy年MM月dd日";

	public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";

	public final static String MONTH_FORMAT = "yyyy-MM";

	public final static String DAY_FORMAT = "yyyyMMdd";

	public final static String DAY_FORMAT_SHORT = "yyMMdd";

	public final static String LONG_FORMAT = "yyyyMMddHHmmss";
	
	
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
	 * @Description: 字符串转换成日期格式
	 * @author yang_hui 
	 * @Created 2016年7月28日
	 * @param currDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String currDate, String format) throws ParseException {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);

			return dtFormatdB.parse(currDate);

		}
	}


	
	public static String dateToString(Date currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);

			return dtFormatdB.format(currDate);
		}
	}
	
	/**
	 * 
	 * @Description:在当前日期上加几个月或减几个月：n == 2 加两个月，n==-2减两个月
	 * @author yang_hui 
	 * @Created 2016年7月28日
	 * @param dateStr
	 * @param n
	 * @return
	 */
	public static String getMonthAfterOrBefore(String dateStr,int n){
		String s = "";
		Date curDate;
		try {
			curDate = stringToDate(dateStr, DATE_FORMAT);
			Calendar cal = Calendar.getInstance();
			cal.setTime(curDate);
			cal.add(Calendar.MONTH, n);
			s = dateToString(cal.getTime(), DATE_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return s;
		
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(getCurrDateTime());
		System.out.println(getCurrDate());
		System.out.println(getCurrTimestamp());
		System.out.println(dateToString(getCurrDateTime(),null));
		System.out.println(getMonthAfterOrBefore("2017-01-26",2));
	}
	
//	Calendar cal = Calendar.getInstance();
//	cal.setTime(curDate);
//	cal.add(Calendar.DAY_OF_MONTH, iDate);
//	return cal.getTime();
}
