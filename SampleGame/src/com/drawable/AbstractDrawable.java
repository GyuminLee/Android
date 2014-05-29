package com.drawable;

import java.io.PrintStream;
import java.io.Serializable;

public abstract class AbstractDrawable implements Drawable, Serializable {

	protected int x, y;
	
	protected OnDrawableChangeListener mListener;
	
	public void setOnDrawableChangeListener(OnDrawableChangeListener listener) {
		mListener = listener;
	}
	
	@Override
	public void incPosition() {
		setPosition(x+10, y+10);
	}
	
	@Override
	public void setPosition(int x, int y) {
		if (x <0 || y < 0) {
			throw new BadPositionException();
		}
		this.x = x;
		this.y = y;
		if (mListener != null) {
			mListener.onDrawableChanged(this);
		}
		onPositionChanged(x,y);
	}
	
	@Override
	public void onPositionChanged(int x, int y) {
	}
	
}
