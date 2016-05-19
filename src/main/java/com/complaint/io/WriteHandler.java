package com.complaint.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteHandler {
	
	public void writeFile(String filePath,String jsonContext) throws IOException{
		writeFile(new File(filePath),jsonContext);
	}
	
	public void writeFile(File file,String jsonContext) throws IOException{
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		out.write(jsonContext.getBytes());
		out.close();
	}

}
