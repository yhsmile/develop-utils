package com.zhaokewen.develop.utils.money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AverageCapitalUtils
 * @Description 等额本金计算工具类。
 * 等额本金则是指在还款期内把贷款数总额等分，每月偿还同等数额的本金和剩余贷款在该月所产生的利息。
 * 每月还本付息金额=(本金/还款月数)+(本金-累计已还本金)×月利率
 * 每月本金=总本金/还款月数
 * 每月利息=(本金-累计已还本金)×月利率
 * 还款总利息= 每月利息累加
 * 还款总额= 还款总利息 + 贷款额
 *
 * @author YangHui
 * @createTime 2017年8月11日-下午2:05:42
 */
public class AverageCapitalUtils {
	
	
	
	
	/**
	 * 
	 * @Description 等额本金还款方式-每月还本付息金额。
	 * 公式：每月还本付息金额=(本金/还款月数)+(本金-累计已还本金)×月利率
	 * @param principal 贷款总金额（本金）
	 * @param yearRate 年利率
	 * @param months 还款期数
	 * @author YangHui
	 * @createTime 2017年8月11日-下午2:13:28
	 * @return Map<Integer,Double>
	 */
	public static Map<Integer,Double> getPerMonthPrincipalInterest(double principal,double yearRate, int months){
		
		Map<Integer,Double> map = new HashMap<Integer,Double>();
		
		//每月需要偿还的本金
		double average = getPerMonthPrincipal(principal, months);
		
		//获取月利率
		double monthRate = yearRate / 12;
		
		//对月利率进行小数点格式化处理：保留6位小数，ROUND_DOWN 表示直接删除多余小数
		monthRate = new BigDecimal(monthRate).setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
		
		for(int i=1;i<=months;i++){
			
			//每月利息
			double preMonthInterest = (principal - average *(i-1)) * monthRate;
			
			//每月还本付息金额
			double perMonth = average + preMonthInterest;
			
			perMonth = new BigDecimal(perMonth).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
			
			map.put(i,perMonth);
			
		}
		
		return map;
		
	}
	
	
	/**
	 * 
	 * @Description 等额本金还款方式-每月偿还利息   
	 * @param principal
	 * @param yearRate
	 * @param months
	 * @return   
	 * @author YangHui
	 * @createTime 2017年8月11日-下午2:45:13
	 * @return Map<Integer,Double>
	 */
	public static Map<Integer,Double> getPerMonthInterest(double principal,double yearRate, int months){
		
		//每月偿还利息Map对象
		Map<Integer, Double> interestMap = new HashMap<Integer,Double>();
		
		//每月需要偿还的本金
		double average = getPerMonthPrincipal(principal, months);
		
		BigDecimal averageBigDecimal = new BigDecimal(average);
		
		//获取每月还本付息Map对象
		Map<Integer, Double> map = getPerMonthPrincipalInterest(principal, yearRate, months);  
		
		
		for(Map.Entry<Integer, Double> entry : map.entrySet()){
		
			//每月还本付息金额
			BigDecimal perMonth = new BigDecimal(entry.getValue());
			
			BigDecimal perMonthInterest = perMonth.subtract(averageBigDecimal);
			
			interestMap.put(entry.getKey(), perMonthInterest.doubleValue());
		}
		
		return interestMap;
		
	}
	
	
	/**
	 * 
	 * @Description 等额本金还款方式-偿还利息总和
	 * @param principal
	 * @param yearRate
	 * @param months
	 * @return   
	 * @author YangHui
	 * @createTime 2017年8月11日-下午2:45:13
	 * @return Map<Integer,Double>
	 */
	public static double getInterestCount(double principal,double yearRate, int months){
		
		Map<Integer, Double> interestMap = getPerMonthInterest(principal, yearRate, months);
		
		BigDecimal count = new BigDecimal(0);  
		
		for(Map.Entry<Integer, Double> entry : interestMap.entrySet()){
			count = count.add(new BigDecimal(entry.getValue()));
		}
		
		return count.doubleValue();
		
	}
	
	
	/**
	 * 
	 * @Description 等额本金每月偿还本金
	 * 公式：每月本金=总本金/还款月数
	 * @param principal 贷款总金额（本金）
	 * @param months	还款期数
	 * @author YangHui
	 * @createTime 2017年8月11日-下午2:08:57
	 * @return double
	 */
	public static double getPerMonthPrincipal(double principal,int months){
		BigDecimal average = new BigDecimal(principal).divide(new BigDecimal(months), 2, BigDecimal.ROUND_DOWN);
		return average.doubleValue();
	}
	
	
	/**
	 * 
	 * @Description 查询某个具体分期期数的利息   
	 * @param principal 贷款总金额（本金）
	 * @param yearRate 年利率
	 * @param months 还款期数
	 * @param month 具体期数
	 * @return   
	 * @author YangHui
	 * @createTime 2017年8月11日-下午3:54:42
	 * @return double
	 */
	public static double getPerMonthInterestOnly(double principal,double yearRate, int months,int month){
		
		Map<Integer, Double> interestMap = getPerMonthInterest(principal, yearRate, months);
		
		
		if(month > months){
			
			System.out.println("输入的分期月数不在分期范围内");
			
			return 0;
		}
		
		return interestMap.get(month);
		
	}
	
	
	
	
	public static void main(String[] args) {
		double principal = 1000000;
		double yearRate = 0.0615;
		int months = 360;
		
		System.out.println(getInterestCount(principal, yearRate, months));
		
		
		System.out.println(getPerMonthInterestOnly(principal, yearRate, months, 361));
		
	}

}
