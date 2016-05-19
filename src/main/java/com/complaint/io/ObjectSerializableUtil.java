package com.complaint.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.complaint.model.ConfigColor;

public class ObjectSerializableUtil {

	public static void exists(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void serialize(Object object, String filePaht) {
		exists(filePaht);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(filePaht)));
			oos.writeObject(object);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Object deserialize(String filePaht) {
		ObjectInputStream ois = null;
		try {
			FileInputStream is = new FileInputStream(filePaht);
			ois = new ObjectInputStream(is);
			Object obj = ois.readObject();
			ois.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static void write(File file, String jsonContext) throws IOException {
		WriteHandler wh = new WriteHandler();
		wh.writeFile(file, jsonContext);
	}

	public static void write(String filePath, String jsonContext)
			throws IOException {
		WriteHandler wh = new WriteHandler();
		wh.writeFile(new File(filePath), jsonContext);
	}

	public static byte[] read(String filePath) throws IOException {
		InputHandler in = new InputHandler();
		return in.readFile(filePath);
	}

	public static ConfigColor readObject(String filePath) throws IOException {
		exists(filePath);
		InputHandler in = new InputHandler();
		ConfigColor color = new ConfigColor();
		byte[] buff = in.readFile(filePath);
		if (buff == null || buff.length == 0) {
			return null;
		}
		String colorStr = new String(buff);
		JSONObject json = (JSONObject) JSONValue.parse(colorStr);
		String vi = (String) json.get("vision");
		color.setVision(vi);
		return color;
	}

}
