package com.zhaokewen.develop.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
* Filename: ObjectUtils.java  
* Description: 对象工具类
* Copyright:Copyright (c)2016
* Company:  ZITO 
* @author:  yang_hui
* @version: 1.0  
* @Create:  2016年7月4日  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2016年7月4日 上午9:34:54				yang_hui  	1.0
 */
public class ObjectUtils {
	
	/**
	 * 
	 * @Description:判断List不为空
	 * @author yang_hui 
	 * @Created 2016年7月4日
	 * @param list
	 * @return
	 */
	public static boolean listIsNotNull(List<?> list){
		if(null == list || list.size() == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @Description:判断某个类里是否有某个方法
	 * @author yang_hui 
	 * @Created 2016年7月4日
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static boolean isHaveSuchMethod(Class<?> clazz,String methodName){
		Method[] methodArray = clazz.getMethods();
		boolean result = false;
		if(null != methodArray){
			for(int i=0;i<methodArray.length;i++){
				if(methodArray[i].getName().equals(methodName)){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: 将 Map<String,Object> 类型转换成实体类
	 * @author yang_hui 
	 * @Created 2016年7月4日
	 * @param clazz
	 * @param map
	 * @return
	 */
	public static <T> T mapToObject(Class<T> clazz,Map<String,Object> map){
		if(null == map){
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		Field field;
		T o = null;
		
		try {
			o = clazz.newInstance();
		} catch (Exception e) {
			
		}
		
		 for(int i=0;i<fields.length;i++){
			 field = fields[i];
			 String fieldName = field.getName();
			 //把属性第一个字母处理成大写
			 String stringLetter = fieldName.substring(0, 1).toUpperCase();
			 //取得set方法名
			 String setName = "set" + stringLetter + fieldName.substring(1);
			 //真正取得get方法
			 Method setMethod = null;
			 Class<?> fieldClass = field.getType();
			 try {
				 Object value = map.get(fieldName); //从 map 对象中取出 key:fieldName 对应的 value 
				 if(null != value && String.valueOf(value).trim().length() > 0 && isHaveSuchMethod(clazz, setName)){
					 if(fieldClass == String.class){ //字符型
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, String.valueOf(value));
						 
					 }else if(fieldClass == Integer.class || fieldClass == int.class){ //整型
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, Integer.parseInt(String.valueOf(value)));
						 
					 }else if(fieldClass == Boolean.class || fieldClass == boolean.class){ //布尔型
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, Boolean.getBoolean(String.valueOf(value)));
						 
					 }else if(fieldClass == Short.class || fieldClass == short.class){ //短整型
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, Short.parseShort(String.valueOf(value)));
						 
					 }else if(fieldClass == Long.class || fieldClass == long.class){ //长整型
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, Long.parseLong(String.valueOf(value)));
						 
					 }else if(fieldClass == Double.class || fieldClass == double.class){ //双精度
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, Double.parseDouble(String.valueOf(value)));
						 
					 }else if(fieldClass == Float.class || fieldClass == float.class){ //单精度
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, Float.parseFloat(String.valueOf(value)));
						 
					 }else if(fieldClass == BigInteger.class){ //
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, BigInteger.valueOf(Long.parseLong(String.valueOf(value))));
						 
					 }else if(fieldClass == BigDecimal.class){ //
						 setMethod = clazz.getMethod(setName, fieldClass);
						 setMethod.invoke(o, BigDecimal.valueOf(Double.parseDouble(String.valueOf(value))));
						 
					 }else if(fieldClass == Date.class){ //日期类型
						 setMethod = clazz.getMethod(setName, fieldClass);
						 if(map.get(fieldName).getClass() == java.sql.Date.class){
							 setMethod.invoke(o, new Date(((java.sql.Date) value).getTime()));
						 }else if(map.get(fieldName).getClass() == java.sql.Time.class){
							 setMethod.invoke(o, new Date(((java.sql.Time) value).getTime()));
						 }else if(map.get(fieldName).getClass() == java.sql.Timestamp.class){
							 setMethod.invoke(o, new Date(((java.sql.Timestamp) value).getTime()));
						 }
					 }else if(fieldClass == List.class){
						 
					 }
				 }
				 
			} catch (Exception e) {
			}
		 }
		return o;
	}
	
	
	
	
	

}
