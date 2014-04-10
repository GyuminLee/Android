package com.example.sample3viewanimation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Animation3D extends Animation {

	int centerX, centerY;
	int width, height;
	Camera mCamera;
	
	public Animation3D() {
		mCamera = new Camera();
	}
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		centerX = width /2;
		centerY = height /2;
		this.width = width;
		this.height = height;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		Matrix m = t.getMatrix();
		
		mCamera.save();
		float degree = interpolatedTime * 90;
		mCamera.rotateY(degree);
		mCamera.getMatrix(m);
		mCamera.restore();
		
		m.preTranslate(-centerX, -centerY);
		m.postTranslate(centerX, centerY);
	}
}
