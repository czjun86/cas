package com.complaint.test;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SerialnoTest {
	public static void main(String[] args) throws NoSuchAlgorithmException{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss-ms");
		String str = df.format(date);
		StringBuilder sb = new StringBuilder();
		sb.append(str);
//		Random random = new Random();
//		sb.append(random.nextInt(10));
//		sb.append(random.nextInt(10));
		System.out.println(sb.toString());
	}
}
