package com.example.samplesensortest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {

	Bitmap mCompassBitmap;
	float degrees;
	Matrix m;
	
	public CompassView(Context context) {
		super(context);
		init(context);
	}

	public CompassView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		mCompassBitmap = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.comass)).getBitmap();
		m = new Matrix();
		m.reset();
	}

	public void setDegree(float degrees) {
		this.degrees = degrees;
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = mCompassBitmap.getWidth();
		int height = mCompassBitmap.getHeight();
		setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		m.setRotate(-degrees, mCompassBitmap.getWidth() / 2, mCompassBitmap.getHeight() / 2);
		canvas.drawBitmap(mCompassBitmap, m, null);
	}
}
