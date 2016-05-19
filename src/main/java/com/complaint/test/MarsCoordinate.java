package com.complaint.test;


/**
 * com.mars.MarsCoordinate.java
 * <ul>
 * <li>File name : MarsCoordinate.java</li>
 * <li>Description : The detail description of the class</li>
 * <li>Copyright : Copyright(C)2008-2013</li>
 * <li>Company : CST</li>
 * <li>remark :</li>
 * <li>create date : 2013-4-8</li>
 * </ul>
 * 
 * @version 1.0
 * @author 许力多
 */
public class MarsCoordinate {

	private final static double PI = 3.14159265358979323;
	private final static double EARTH = 6378245.0;
	private final static double EE = 0.00669342162296594323;

	/**
	 * 是否在中国
	 * 
	 * @param coor
	 * @return
	 */
	public boolean isCoordinateInChina(Coordinate coor) {
		if (coor.lng < 72.004 || coor.lng > 137.8347) {
			return false;
		}
		if (coor.lat < 0.8293 || coor.lat > 55.8271) {
			return false;
		}
		return true;
	}

	/**
	 * 转换lat
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double convertLat(double x, double y) {
		double ret = 0;
		ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2
				* Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;

		return ret;
	}

	/**
	 * 转换lng
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double convertLng(double x, double y) {
		double ret = 0;
		ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
				* Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0
				* PI)) * 2.0 / 3.0;

		return ret;
	}

	public Coordinate convert2Mars(Coordinate earth) {
		// 如果不在中国
		if (!isCoordinateInChina(earth)) {
			return null;
		}
		MarsCoordinate.Coordinate mars = new MarsCoordinate().new Coordinate();

		double lat = convertLat(earth.lng - 105.0, earth.lat - 35.0);
		double lng = convertLng(earth.lng - 105.0, earth.lat - 35.0);
		double radLat = earth.lat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - EE * magic * magic;
		double sqrtMagic = Math.sqrt(magic);

		lat = (lat * 180.0) / ((EARTH * (1 - EE)) / (magic * sqrtMagic) * PI);
		lng = (lng * 180.0) / (EARTH / sqrtMagic * Math.cos(radLat) * PI);

		mars.lat = earth.lat + lat;
		mars.lng = earth.lng + lng;
		return mars;
	}

	public class Coordinate {
		public double lng;
		public double lat;
	}
}
