package com.example.testgooglemapsample;

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

	Paint mPaint = new Paint();
	
	float mRotateDegrees = 0;
	
	public CompassView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public CompassView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		compass = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.comass)).getBitmap();
	}
	
	public void setRotateDegrees(float rotateDegrees) {
		mRotateDegrees = rotateDegrees;
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int width = compass.getWidth();
		int height = compass.getHeight();
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int centerX = compass.getWidth() / 2;
		int centerY = compass.getHeight() / 2;
		
		canvas.save();
		
		canvas.rotate(-mRotateDegrees, centerX, centerY);
		
		canvas.drawBitmap(compass, 0, 0, mPaint);
		
		canvas.restore();
	}
	
	
}
