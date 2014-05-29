package com.shape;

import java.io.Serializable;

public class Point implements Serializable {
	int x;
	int y;
	
	public Point(int x, int y) throws BadPointException {
		if (x < 0 || y < 0) {
			throw new BadPointException();
		}
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
