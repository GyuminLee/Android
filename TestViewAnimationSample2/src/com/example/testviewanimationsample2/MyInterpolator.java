package com.example.testviewanimationsample2;

import android.graphics.Interpolator;

public class MyInterpolator implements android.view.animation.Interpolator {

	@Override
	public float getInterpolation(float time) {
		// TODO Auto-generated method stub
		float fraction = time;
		return fraction;
	}

}
