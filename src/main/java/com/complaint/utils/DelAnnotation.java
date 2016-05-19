package com.complaint.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class DelAnnotation {
	private static String PROJECTPATH = "F:/myworkspace/TSSC";
	private static String MODELPATH = PROJECTPATH+"/src/main/java/com/complaint/model/";
	private static String DAOPATH = PROJECTPATH+"/src/main/java/com/complaint/dao/";
	private static String MAPPERPATH =PROJECTPATH+ "/src/main/resources/com/complaint/dao/mapping/";
	/**
	 * 
	 * @param oldFile
	 * @param newFile
	 * @param className
	 *            类名
	 * @param type
	 *            0:model 1:dao 2:mapper
	 */
	public static void copyFile(File oldFile, File newFile, String className,
			int type) {
		BufferedReader reader = null;
		FileWriter fileWriter = null;
		try {
			reader = new BufferedReader(new FileReader(oldFile));
			fileWriter = new FileWriter(newFile);
			String tempString = null;
			int line = 1;
			boolean tag = true;
			boolean delimport = false;
			boolean examTag = false;
			String examStr = "";
			while ((tempString = reader.readLine()) != null) {
				if (type == 2) {
					if (tempString.contains("<!--")) {
						tag = false;
					}
					if(tempString.contains(className+"Mapper")){
						tempString = tempString.replace(className+"Mapper", className+"Dao");
					}
					if(tempString.contains("Example") && !examTag){
						examTag = true;
						examStr = tempString.substring(tempString.indexOf("<")+1,tempString.indexOf(" ", tempString.indexOf("<")));
					}
					
					if (tag && !examTag) {
						fileWriter.write(tempString + "\n");
					}
					if (tempString.contains("-->")) {
						tag = true;
					}
					if(examStr != null && examStr != ""){
						if(tempString.contains("</"+examStr+">")){
							examTag = false;
						}
					}
					line++;
				} else {
					delimport = false;
					if (tempString.contains("/**")) {
						tag = false;
					}
					if (tag) {
						if (type == 1) {
							if (tempString.indexOf(className + "Mapper") != -1) {
								tempString = tempString.replaceAll(className
										+ "Mapper", className + "Dao");
							}
							if (tempString.indexOf(className + "Example") != -1) {
									delimport = true;
							}
							if(tempString.indexOf("record") != -1){
								tempString = tempString.replaceAll(
										"record",className.substring(0, 1).toLowerCase()
										+ className.substring(1,
												className.length()));
							}
							if (tempString.indexOf("example") != -1) {
								tempString = tempString.replaceAll(
										"example",className.substring(0, 1).toLowerCase()
										+ className.substring(1,
												className.length()));
							}
							if(!delimport){
								fileWriter.write(tempString + "\n");
							}
						}else{							
							fileWriter.write(tempString + "\n");
						}
					}
					if (tempString.contains("*/")) {
						tag = true;
					}
					line++;
				}

			}
			fileWriter.close();
			reader.close();
			oldFile.delete();
		} catch (IOException e) {
			System.out.println("文件删除失败："+e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
					fileWriter.close();
				} catch (IOException e1) {
					System.out.println("流关闭异常："+e1.getMessage());
				}
			}
		}
		
	}
	/**
	 * 删除所有注释
	 * @param table
	 */
	public static void delAllAnnotation(String table) {
		delModelAnnotation(table);
		delDaoAnnotation(table);
		delXmlAnnotation(table);
		System.out.println("注释清理成功！");
	}
	/**
	 * 删除类中的注释
	 * @param table
	 */
	public static void delModelAnnotation(String table){
		File oldFile = new File(MODELPATH + table + ".java");
		File newFile = new File(MODELPATH + table + "Temp1.java");
		File tempFile = new File(MODELPATH + table + "Example.java");
		tempFile.delete();
		copyFile(oldFile, newFile, table, 0);
		copyFile(newFile, oldFile, table, 0);
	}
	/**
	 * 删除dao中的注释
	 * @param table
	 */
	public static void delDaoAnnotation(String table){
		File oldDaoFile = new File(DAOPATH + table + "Mapper.java");
		File newDaoFile = new File(DAOPATH + table + "Dao.java");
		copyFile(oldDaoFile, newDaoFile, table, 1);
	}
	/**
	 * 删除xml中的注释
	 * @param table
	 */
	public static void delXmlAnnotation(String table){
		File oldMapperFile = new File(MAPPERPATH + table + "Mapper.xml");
		File newMapperFile = new File(MAPPERPATH + table + "MapperTemp1.xml");
		copyFile(oldMapperFile, newMapperFile, table, 2);
		copyFile(newMapperFile, oldMapperFile, table, 2);
	}
	
	public static void main(String[] args) {
		/*InputStream in =new DelAnnotation().getClass().getClassLoader()
				.getResourceAsStream("classname.properties"); 
		Properties pro = new Properties();
		try {
			pro.load(in);
			delAllAnnotation(pro.getProperty("classname"));
		} catch (IOException e) {
			System.out.println("出错了！！");
		}*/
		delAllAnnotation("Scene");
	}
}
