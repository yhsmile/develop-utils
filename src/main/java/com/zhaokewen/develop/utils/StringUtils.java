package com.zhaokewen.develop.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * 
* Filename: StringUtils.java  
* Description: 字符工具类
* Copyright:Copyright (c)2016
* Company:  sun3d 
* @author:  yanghui
* @version: 1.0  
* @Create:  2016年6月30日  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2016年6月30日 上午11:54:56				yanghui  	1.0
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
	
	
	/**
	 * 
	 * @Description:生成32位uuid
	 * @author yang_hui 
	 * @Created 2016年7月4日
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 生成六位随机数
	 * @return
	 */
	public static Integer getRandomSix(){
		Random ran = new Random();
		int r = 0;
		m1:while(true){
			int n = ran.nextInt(1000000);
			r = n;
			int[] bs = new int[6];
			for(int i=0;i<bs.length;i++){
				bs[i] = n%10;
				n/=10;
			}
			Arrays.sort(bs);
			for(int i=1;i<bs.length;i++){
				if(bs[i-1] == bs[i]){
					continue m1;
				}
			}
			break;
		}
		return r;
	}
	
	public static void main(String[] args) {
	//	System.out.println(getRandomSix());
		System.out.println(getUUID());
	}
}
