package com.zhaokewen.develop.utils.money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoanInterestUtils
 * @Description 金融-贷款利息计算工具类
 * @author YangHui
 * @createTime 2017年8月11日-下午1:29:55
 */
public class LoanInterestUtils {
	
	
	/**
	 * 
	 * @Description 等额本息还款方式 。 
	 * 	等额本息是指，把按揭贷款的本金总额与利息总额相加，然后平均分摊到还款期限的每个月中。
	 * 	等额本息贷款采用的是复合利率计算。在每期还款的结算时刻，剩余本金所产生的利息要和剩余的本金(贷款余额)一起被计息。
	 * 
	 *  公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕  
	 *  
	 * @param principal	贷款总金额（本金）
	 * @param yearRate	年利率
	 * @param month	还款期数
	 * @author YangHui
	 * @createTime 2017年8月11日-下午1:34:56
	 * @return double 每月偿还本金和利息,不四舍五入，直接截取小数点最后两位
	 */
	public static double equalPrincipalPlusInterest(double principal,double yearRate,int month){
		
		//月利率
		double monthRate = yearRate / 12;
		
		//(1＋月利率)＾还款月数 
		double powRate = Math.pow(1 + monthRate, month);
		
		//贷款本金×月利率×(1＋月利率)＾还款月数
		
		BigDecimal monthIncome = new BigDecimal(principal)
				.multiply(new BigDecimal(monthRate * powRate))
				.divide(new BigDecimal(powRate - 1), 2, BigDecimal.ROUND_DOWN);
		
		return monthIncome.doubleValue();
				
		
	}
	
	/**
	 * 
	 * @Description 等额本息计算获取还款方式为等额本息的每月偿还利息  
	 * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕   
	 * @param invest 总借款额（贷款本金）
	 * @param yearRate 年利率
	 * @param totalmonth 还款期数
	 * @author YangHui
	 * @createTime 2017年8月11日-下午4:10:12
	 * @return Map<Integer,BigDecimal>
	 */
	public static Map<Integer, BigDecimal> getPerMonthInterest(double invest, double yearRate, int totalmonth) {  
	  
        Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
        
        //月利率
        double monthRate = yearRate/12;
        
        //
        BigDecimal monthInterest; 
        
        for (int i = 1; i < totalmonth + 1; i++) {  
        	
            BigDecimal multiply = new BigDecimal(invest).multiply(new BigDecimal(monthRate));  
            
            BigDecimal sub  = new BigDecimal(Math.pow(1 + monthRate, totalmonth)).subtract(new BigDecimal(Math.pow(1 + monthRate, i-1))); 
            
            monthInterest = multiply.multiply(sub).divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 6, BigDecimal.ROUND_DOWN);  
            
            monthInterest = monthInterest.setScale(2, BigDecimal.ROUND_DOWN);  
            
            map.put(i, monthInterest);  
        }  
        return map;  
    } 
	
	
	/**
	 * 
	 * @Description 等额本息计算获取还款方式为等额本息的每月偿还本金     
	 * @param invest 总借款额（贷款本金）
	 * @param yearRate 年利率
	 * @param totalmonth 还款期数
	 * @author YangHui
	 * @createTime 2017年8月11日-下午4:11:34
	 * @return Map<Integer,BigDecimal>
	 */
	public static Map<Integer, BigDecimal> getPerMonthPrincipal(double invest, double yearRate, int totalmonth) {  
		
		//月利率
        double monthRate = yearRate / 12;  
        
        BigDecimal monthIncome = new BigDecimal(invest)  
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))  
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_DOWN);  
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);  
        Map<Integer, BigDecimal> mapPrincipal = new HashMap<Integer, BigDecimal>();  
  
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {  
            mapPrincipal.put(entry.getKey(), monthIncome.subtract(entry.getValue()));  
        }  
        return mapPrincipal;  
    } 
	
	/**
	 * 
	 * @Description 应还利息总和   
	 * @param invest
	 * @param yearRate
	 * @param totalmonth
	 * @author YangHui
	 * @createTime 2017年8月11日-下午4:13:59
	 * @return double
	 */
	public static double getInterestCount(double invest, double yearRate, int totalmonth) {  
        BigDecimal count = new BigDecimal(0);  
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);  
  
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {  
            count = count.add(entry.getValue());  
        }  
        return count.doubleValue();  
    }
	
	/**
	 * 
	 * @Description 应还本金总和   
	 * @param invest
	 * @param yearRate
	 * @param totalmonth
	 * @author YangHui
	 * @createTime 2017年8月11日-下午4:13:59
	 * @return double
	 */
	public static double getPrincipalInterestCount(double invest, double yearRate, int totalmonth) {  
	        double monthRate = yearRate / 12;  
	        BigDecimal perMonthInterest = new BigDecimal(invest)  
	                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))  
	                .divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_DOWN);  
	        BigDecimal count = perMonthInterest.multiply(new BigDecimal(totalmonth));  
	        count = count.setScale(2, BigDecimal.ROUND_DOWN);  
	        return count.doubleValue();  
	 }
	
	public static void main(String[] args) {
		
		
		double principal = 1000000;
		double yearRate = 0.0615;
		int month = 360; //30年
		
		//测试等额本息
		double income = equalPrincipalPlusInterest(principal, yearRate, month);
		System.out.println(income);
	}
	

	
	
	
}
