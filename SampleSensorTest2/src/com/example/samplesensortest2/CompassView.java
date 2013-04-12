package com.example.samplesensortest2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {

	Bitmap compass;
	Matrix rotateMatrix;
	Paint mPaint = new Paint();
	
	public CompassView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CompassView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	private void init(Context context) {
		compass = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.comass)).getBitmap();
		rotateMatrix = new Matrix();
		rotateMatrix.reset();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(compass, rotateMatrix, mPaint);
	}
	
	public void setOrientation(float angle) {
		int centerX = compass.getWidth() / 2;
		int centerY = compass.getHeight() / 2;
		rotateMatrix.setRotate(angle, centerX, centerY);
		invalidate();
	}
}
