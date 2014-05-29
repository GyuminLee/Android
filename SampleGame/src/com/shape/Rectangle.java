package com.shape;

import java.util.Random;

public class Rectangle extends Shape {

	int left, right, top, bottom;
	
	public Rectangle() {
		// Generate Rectangle
		Random r = new Random();
		left = r.nextInt(50);
		right = 50 + r.nextInt(50);
		top = r.nextInt(50);
		bottom = 50 + r.nextInt(50);
		setBounds();
	}
	
	public Rectangle(int left , int top, int right, int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		setBounds();
	}
	
	protected void setBounds() {
		boundsLeft = left;
		boundsRight = right;
		boundsTop = top;
		boundsBottom = bottom;
	}
	@Override
	public int calculateArea() {
		return Math.abs((right - left) * (bottom - top));
	}

	@Override
	public String toString() {
		return "type : Rectangle , left : " + left + ", top : " + top + ", right : " + right + ", bottom : " + bottom;
	}
}
