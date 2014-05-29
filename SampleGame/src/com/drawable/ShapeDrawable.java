package com.drawable;

import java.io.PrintStream;

import com.annotation.Friend;
import com.shape.Shape;

@Friend
public class ShapeDrawable extends AbstractDrawable {

	Shape s;
	
	public void setShape(Shape s) {
		this.s = s;
	}
	
	@Override
	public void draw(PrintStream out) {
		out.println("position : " + x + "," + y);
		out.println("Shape : " + s);
	}

}
