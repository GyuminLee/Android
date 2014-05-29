package com.shape;

import java.util.Random;

public class Circle extends Shape {

	Point center;
	int radius;
	public Circle() throws BadPointException {
		Random r = new Random();
		int x = 20 + r.nextInt(30);
		int y = 20 + r.nextInt(30);
		center = new Point(x,y);
		int min = Math.min(x, y);
		radius = 20 + r.nextInt(min - 20);
		setBounds();
	}
	
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
		setBounds();
	}
	
	protected void setBounds() {
		boundsLeft = center.getX() - radius;
		boundsRight = center.getX() + radius;
		boundsTop = center.getY() - radius;
		boundsBottom = center.getY() + radius;
	}
	
	@Override
	public int calculateArea() {
		return (int)(radius * radius * Math.PI);
	}

}
