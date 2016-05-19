package com.complaint.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author jyNine
 *
 */
public class PatternUtil {
	private static Pattern FLOATPATTERN = Pattern.compile("^(-?\\d+)(\\.\\d{1,2})?$");
	/**
	 * 验证是否是float类型
	 * @param input
	 * @return
	 */
	public static boolean isFloat(String input){
		Matcher matcher = FLOATPATTERN.matcher(input);
		return matcher.matches();
	}
	
	public static String formate(String str){
		if(str != null && !"".equals(str)){			
			str = str.replaceAll(">", "&gt;").replaceAll("<", "&lt;");
		}
		return str;
	}
	/**
	 * 两个double计算百分比
	 * @param solv
	 * @param complain
	 * @return
	 */
	public static double getSolvRate(double numerator ,double denominator ){
		double re = 0;
		double a = Math.round(numerator);
		double b = Math.round(denominator);
		re = (a/b)*10000;
		re = Math.round(re);
		re = re/100;
		return re;
	}
	
	/**
	 * 去掉两位小数末尾的0
	 * @param param 
	 * @return 带%的String
	 */
	public static String getDouble(double param){
		DecimalFormat df = new DecimalFormat();
		String style = "0.00";
		df.applyPattern(style);
		String res = df.format(param);
		String test = res.substring(res.length()-1,res.length());
		if(test.equals("0")){
			style = "0.0";
			df.applyPattern(style);
			res = df.format(param);
			test = res.substring(res.length()-1,res.length());
			if(test.equals("0")){
				style = "0";
				df.applyPattern(style);
				res = df.format(param);
			}
		}
		return res+"%";
	}
}
