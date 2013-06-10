package com.example.testviewanimationsample2;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class My3DAnimation extends Animation {

	int mCenterX, mCenterY;
	
	Camera mCamera;
	
	public My3DAnimation() {
		// TODO Auto-generated constructor stub
		mCamera = new Camera();
	}
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCenterX = width  / 2;
		mCenterY = height / 2;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Matrix m = t.getMatrix();
//		t.setAlpha(1 - interpolatedTime);
		mCamera.save();
		
		if (interpolatedTime < 0.5) {
			mCamera.rotateY(90 * interpolatedTime);
		} else {
			mCamera.rotateY(90 * ( 1 - interpolatedTime));
		}
		
		mCamera.getMatrix(m);
		
		mCamera.restore();
				
		m.preTranslate(-mCenterX, -mCenterY);
		m.postTranslate(mCenterX, mCenterY);
	}
}
