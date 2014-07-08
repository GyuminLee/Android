package com.example.sample4viewanimation;

import android.graphics.Camera;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class My3DAnimation extends Animation {

	int mWidth, mHeight;
	Camera mCamera;
	
	public My3DAnimation() {
		mCamera = new Camera();
	}
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mWidth = width;
		mHeight = height;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		
		mCamera.save();
		mCamera.rotateY(interpolatedTime * 45);
		mCamera.getMatrix(t.getMatrix());
		mCamera.restore();
		
		t.getMatrix().preTranslate(-mWidth/2, -mHeight/2);
		t.getMatrix().postTranslate(mWidth/2, mHeight/2);
	}
}
