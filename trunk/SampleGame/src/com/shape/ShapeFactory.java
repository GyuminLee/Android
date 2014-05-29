package com.shape;

import java.util.Random;

public class ShapeFactory {

	public static final int TYPE_COUNT = 4;
	public static final int TYPE_TRIANGLE = 0;
	public static final int TYPE_RECTANGLE = 1;
	public static final int TYPE_POLYGON = 2;
	public static final int TYPE_CIRCLE = 3;

	static Random r = new Random();

	public static Shape createShape() throws BadPointException {
		int type = r.nextInt(TYPE_COUNT);
		Shape s;
		switch (type) {
		case TYPE_TRIANGLE:
			s = new Triangle();
			break;
		case TYPE_RECTANGLE:
			s = new Rectangle();
			break;
		case TYPE_POLYGON:
			s= new Polygon();
			break;
		case TYPE_CIRCLE:
			s = new Circle();
			break;
		default:
			s = new Triangle();
			break;
		}
		return s;
	}
}
