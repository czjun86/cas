package com.complaint.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class DateUtils {

	public static String getNowByFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static String dateFormat(String date) {
		String newDate = "";
		String[] arr = date.split("-");
		newDate = arr[0] + "年" + arr[1] + "月" + arr[2] + "日";
		return newDate;
	}
	/**
	 * 初始化时间(yyyy-MM-dd)
	 * @param 
	 */
	public  static String getDayTime(Date date,Integer day){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		return date2Str(c.getTime(), "yyyy-MM-dd");
	}
	/**
	 * 日期转换成为字符串，注意，格式中的月份为大写M，分钟为小写m
	 * 
	 * @param date
	 *            java.util.Date
	 * @param pattern
	 *            String 转换格式，如："yyyy-MM-dd"
	 * @throws ParseException
	 * @return String
	 */
	public  static String date2Str(java.util.Date date, String pattern) {
		try {
			SimpleDateFormat f = new SimpleDateFormat(pattern);
			return f.format(date);
		} catch (Exception ee) {
			ee.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 获取当前月份,今天时间,昨天时间
	 * 
	 */
	public static Map getToday(){
		Map map = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM"); 
		
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek=calendar1.get(Calendar.DAY_OF_WEEK)-1;
		int offset1=1-dayOfWeek;
		int offset2=7-dayOfWeek;
		calendar1.add(Calendar.DATE, offset1-7);
		calendar2.add(Calendar.DATE, offset2-7);
		Calendar calendar3 = Calendar.getInstance();
		calendar3.add(Calendar.MONTH, -1);
		map.put("m_time", sdfm.format(calendar3.getTime()));//上月
		map.put("s_time", sdf.format(calendar1.getTime()));//上周一
		map.put("e_time", sdf.format(calendar2.getTime()));//上周日
		return map;
	}
}
