package com.example.helloviewanimationtest;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyAnimation extends Animation {
	
	int mCenterX, mCenterY;
	Camera mCamera;
	
	public MyAnimation() {
		super();
		mCamera = new Camera();
	}
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCenterX = width / 2;
		mCenterY = height / 2;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		Matrix m = t.getMatrix();
		
		mCamera.save();
		mCamera.rotateY(90 * interpolatedTime);
		mCamera.getMatrix(m);
		mCamera.restore();
		
		m.preTranslate(-mCenterX, -mCenterY);
		m.postTranslate(mCenterX, mCenterY);
	}
}
