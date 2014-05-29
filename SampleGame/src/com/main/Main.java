package com.main;

import com.drawable.GroupDrawable;
import com.drawable.ImageDrawable;
import com.drawable.ShapeDrawable;
import com.image.Image;
import com.shape.BadPointException;
import com.shape.Circle;
import com.shape.Rectangle;
import com.shape.Shape;
import com.shape.ShapeFactory;

public class Main {

	public static final int SHAPE_COUNT = 100;
	Shape[] shapes;
	
	
	public Main() {
		shapes = new Shape[SHAPE_COUNT];
		for (int i = 0; i < shapes.length; i++) {
			try {
				shapes[i] = ShapeFactory.createShape();
			} catch (BadPointException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				shapes[i] = new Rectangle(10, 10, 90, 90);
			}
		}
		Game g = new Game();
		GroupDrawable<ShapeDrawable> group = new GroupDrawable<ShapeDrawable>();

		try {
			ShapeDrawable sd = new ShapeDrawable();
			sd.setShape(ShapeFactory.createShape());
			g.setDrawable(sd);
		} catch (BadPointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for (Shape s : shapes) {
			ShapeDrawable sd = new ShapeDrawable();
			sd.setShape(s);
//			g.setDrawable(sd);
			group.addChild(sd);
		}
		g.setDrawable(group);
	
		Image i = new Image();
		ImageDrawable id = new ImageDrawable();
		id.setImage(i);
		
		g.setDrawable(id);
		
		MoveRunnable moveRunnable = new MoveRunnable();
		moveRunnable.setGame(g);
		moveRunnable.start();
	}
	
	public void printSumArea() {
		int sum = 0;
		for (int i = 0; i < shapes.length; i++) {
			sum += shapes[i].calculateArea();
		}
		
		System.out.println("Sum : " + sum);
	}
	
	public void printShapes() {
		System.out.println("Shapes["+shapes.length+"]");
		for (int i = 0; i < shapes.length; i++) {
			System.out.println(shapes[i]);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.printShapes();
		main.printSumArea();
	}

}
