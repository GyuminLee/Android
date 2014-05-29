package com.shape;

import java.util.Random;

public class Triangle extends Shape {

	Point[] point;
	
	int sum = NOT_CALCULATE;
	
	public Triangle() throws BadPointException {
		// auto Generate Point
		point = new Point[3];
		Random r = new Random();
		for (int i = 0; i < point.length; i++) {
			int x = r.nextInt(100);
			int y = r.nextInt(100);
			point[i] = new Point(x,y);
		}
		setBounds();
	}
	
	public Triangle(Point p1, Point p2, Point p3) {
		point = new Point[3];
		point[0] = p1;
		point[1] = p2;
		point[2] = p3;
		setBounds();
	}
	
	@Override
	protected void setBounds() {
		calculateArea();
	}
	
	
	@Override
	public int calculateArea() {
		if (sum == NOT_CALCULATE) {
			sum = calculateBaseArea(point[0], point[1], point[2]);
		}
		return sum;
	}
	
	@Override
	public String toString() {
		return "Type : Triangle, Point : " + point;
	}

}
