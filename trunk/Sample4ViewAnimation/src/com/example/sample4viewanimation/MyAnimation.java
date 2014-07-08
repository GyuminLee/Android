package com.example.sample4viewanimation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyAnimation extends Animation {
	int mWidth, mHeight;
	int mParentWidth, mParentHeight;
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mWidth = width;
		mHeight = height;
		mParentWidth = parentWidth;
		mParentHeight = parentHeight;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		float fx = interpolatedTime;
		float fy = fx * fx;
		
		t.getMatrix().setTranslate(fx * mParentWidth, fy * mParentHeight);
		
	}
}
