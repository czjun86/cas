package com.complaint.utils;

public class StringUtils {
	
	/**
	 * 计算中英文混合的字节长度
	 * @throws Exception 
	 */
	public static Integer strByte(String str) throws Exception{
		if(str!=null&&!("".equals(str))){
			str=new String(str.getBytes("gb2312"),"iso-8859-1");
			return str.length();
		}else{
			return 0;
		}
	}
}
