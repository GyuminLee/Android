package com.drawable;

import java.io.PrintStream;

public interface Drawable {
	public void incPosition();
	public void setPosition(int x, int y);
	public void draw(PrintStream out);
	public void onPositionChanged(int x, int y);
}
