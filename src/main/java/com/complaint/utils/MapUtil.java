package com.complaint.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.complaint.model.LLOffset;
import com.complaint.model.MyLLOffset;
import com.complaint.test.MarsCoordinate;
import com.complaint.test.MarsCoordinate.Coordinate;

public class MapUtil {

	private static Map<String, LLOffset> offsetMap = new HashMap<String, LLOffset>();

	public static Map<String, LLOffset> getOffsetMap() {
		return offsetMap;
	}

	public static void setOffsetMap(Map<String, LLOffset> offsetMap) {
		MapUtil.offsetMap = offsetMap;
	}
	/**
	 * 数据库纠偏
	 * @param lng
	 * @param lat
	 * @return
	 */
	public static LLOffset getLLOffset(BigDecimal lng, BigDecimal lat) {
		String lngstr = String.valueOf(lng.doubleValue());
		String latstr = String.valueOf(lat.doubleValue());
		String ll = lngstr.substring(0,lngstr.indexOf(".")+2)+"X"+latstr.substring(0, latstr.indexOf(".")+2);
		return offsetMap.get(ll);
	}
	/**
	 * 算法纠偏
	 * @param lng
	 * @param lat
	 * @return
	 */
	public static LLOffset Convert2Mars(BigDecimal lng,BigDecimal lat) {
		MarsCoordinate mci = new MarsCoordinate();
		MarsCoordinate.Coordinate earth = mci.new Coordinate();
		earth.lat = lat.doubleValue();
		earth.lng = lng.doubleValue();
		MarsCoordinate.Coordinate mars = mci.convert2Mars(earth);
		LLOffset llOffset = new LLOffset();
		llOffset.setLat(new BigDecimal(mars.lat));
		llOffset.setLng(new BigDecimal(mars.lng));
		return llOffset;
	}
	/**
	 * 利用算法纠偏得到偏移后的经纬度
	 * @param mapType 经纬度类型   1  百度   0 google google需要纠偏
	 * @param lng
	 * @param lat
	 * @return
	 */
	public static MyLLOffset getMyLLOffset1(int mapType, BigDecimal lng,
			BigDecimal lat) {
		MyLLOffset myLLOffset = new MyLLOffset();
			if (mapType == 0) {
				LLOffset llOffset = Convert2Mars(lng, lat);	
				myLLOffset.setOldLng(lng);
				myLLOffset.setOldLat(lat);
				myLLOffset.setNewLng(llOffset.getLng());
				myLLOffset.setNewLat(llOffset.getLat());
				myLLOffset.setType(mapType);
			} else {
				myLLOffset.setOldLng(lng);
				myLLOffset.setOldLat(lat);
				myLLOffset.setNewLng(lng);
				myLLOffset.setNewLat(lat);
				myLLOffset.setType(mapType);
			}
		return myLLOffset;
	}
	/**
	 * 用数据库纠偏取得的值 
	 * 如果要使用该方法    需要放开SecurityMetadataSource类中的loadResourceDefine()方法里面的initLLMap();    
	 * @param mapType
	 * @param lng
	 * @param lat
	 * @return
	 */
	public static MyLLOffset getMyLLOffset(int mapType, BigDecimal lng,
			BigDecimal lat) {
		MyLLOffset myLLOffset = new MyLLOffset();
		LLOffset llOffset = getLLOffset(lng, lat);	
		if (llOffset != null) {
			if (mapType == 0) {
				myLLOffset.setOldLng(lng);
				myLLOffset.setOldLat(lat);
				myLLOffset.setNewLng(lng.add(llOffset.getOffsetLng()));
				myLLOffset.setNewLat(lat.add(llOffset.getOffsetLat()));
				myLLOffset.setType(mapType);
			} else {
				myLLOffset.setOldLng(lng.subtract(llOffset.getOffsetLng()));
				myLLOffset.setOldLat(lat.subtract(llOffset.getOffsetLat()));
				/*LLOffset temp = getLLOffset(
						lng.subtract(llOffset.getOffsetLng()),
						lat.subtract(llOffset.getOffsetLat()));
				myLLOffset.setNewLng(lng.add(temp.getOffsetLng()));
				myLLOffset.setNewLat(lat.add(temp.getOffsetLat()));*/
				myLLOffset.setNewLng(lng);
				myLLOffset.setNewLat(lat);
				myLLOffset.setType(mapType);
			}
		}else{
			myLLOffset.setOldLng(lng);
			myLLOffset.setOldLat(lat);
			myLLOffset.setNewLng(lng);
			myLLOffset.setNewLat(lat);
			myLLOffset.setType(mapType);
		}
		return myLLOffset;
	}
}
