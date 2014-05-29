package com.drawable;

import java.io.PrintStream;

import com.annotation.Enemy;
import com.image.Image;

@Enemy
public class ImageDrawable extends AbstractDrawable {

	Image image;
	public void setImage(Image image) {
		this.image = image;
	}
	@Override
	public void draw(PrintStream out) {
		out.println("position : " + x + "," + y);
		out.println("image : " + image);
	}

}
