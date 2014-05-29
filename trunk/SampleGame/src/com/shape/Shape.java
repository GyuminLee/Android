package com.shape;

import java.io.Serializable;

public abstract class Shape implements Serializable {
	public static final int NOT_CALCULATE = -1;
	
	public abstract int calculateArea();
	
	protected int boundsLeft = Integer.MAX_VALUE, boundsTop = Integer.MAX_VALUE, boundsRight = Integer.MIN_VALUE, boundsBottom = Integer.MIN_VALUE;
	
	protected int calculateBaseArea(Point p1, Point p2, Point p3) {
		int minX = Math.min(Math.min(p1.getX(), p2.getX()), p3.getX());
		boundsLeft = Math.min(boundsLeft, minX);
		int maxX = Math.max(Math.max(p1.getX(), p2.getX()), p3.getX());
		boundsRight = Math.max(boundsRight, maxX);
		int minY = Math.min(Math.min(p1.getY(), p2.getY()), p3.getY());
		boundsTop = Math.min(boundsTop, minY);
		int maxY = Math.max(Math.max(p1.getY(), p2.getY()), p3.getY());
		boundsBottom = Math.max(boundsBottom, maxY);
		int rectArea = (maxX - minX) * (maxY - minY);
		int p1p2Area = Math.abs((p2.getX() - p1.getX()) * (p2.getY() - p1.getY()) / 2);
		int p1p3Area = Math.abs((p3.getX() - p1.getX()) * (p3.getY() - p1.getY()) / 2);
		int p2p3Area = Math.abs((p3.getX() - p2.getX()) * (p3.getY() - p2.getY()) / 2);
		return (rectArea - p1p2Area - p1p3Area - p2p3Area);
	}
	
	abstract protected void setBounds();
	
	public int getLeft() {
		return boundsLeft;
	}
	
	public int getRight() {
		return boundsRight;
	}
	
	public int getTop() {
		return boundsTop;
	}
	
	public int getBottom() {
		return boundsBottom;
	}
}
