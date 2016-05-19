package com.complaint.test;


public class MarsCoordinateTest {

	
	public void testConvert2Mars() {
		MarsCoordinate mci = new MarsCoordinate();
		MarsCoordinate.Coordinate earth = mci.new Coordinate();
		earth.lat = 29.56312328;
		earth.lng = 106.54258334;
		MarsCoordinate.Coordinate mars = mci.convert2Mars(earth);
		System.out.println(mars.lat + "," + mars.lng);
	}
	public static void main(String[] args) {
		MarsCoordinateTest t=new MarsCoordinateTest();
		t.testConvert2Mars();
	}
}
