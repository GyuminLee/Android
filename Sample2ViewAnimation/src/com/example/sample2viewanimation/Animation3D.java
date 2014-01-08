package com.example.sample2viewanimation;

import android.graphics.Camera;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Animation3D extends Animation {
	Camera mCamera;
	
	public Animation3D() {
		mCamera = new Camera();
	}
	
	int centerX, centerY;
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		centerX = width / 2;
		centerY = height /2;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
//		t.getMatrix().setSkew(interpolatedTime, 0, centerX, centerY);
		
		mCamera.save();
		mCamera.rotateY(90 * interpolatedTime);
		mCamera.getMatrix(t.getMatrix());
		mCamera.restore();
		
		t.getMatrix().preTranslate(-centerX, -centerY);
		t.getMatrix().postTranslate(centerX, centerY);
	}
}
