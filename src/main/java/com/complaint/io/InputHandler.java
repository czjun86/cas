package com.complaint.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputHandler {
	
	public byte[] readFile(String filePath) throws IOException{
		return readFile(new File(filePath));
	}
	
	public byte[] readFile(File file) throws IOException{
		if(file == null || !file.exists()){
			return null;
		}
		return readFile(new FileInputStream(file));
	}
	
	public byte[] readFile(InputStream in) throws IOException{
		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int len = 0;
		while((len = bis.read(buff)) != -1){
			bos.write(buff, 0, len);
		}
		bis.close();
		bos.close();
		return bos.toByteArray();
	}

}
