package com.example.sampleeventtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	Path mFingerDrawPath;
	float mOldX;
	float mOldY;
	boolean isDowned = false;
	Paint mPaint;
	Paint mOffScreenPaint;
	Bitmap offScreenBitmap;
	Canvas offScreenCanvas;

	public MyView(Context context) {
		this(context, null, 0);
	}

	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mFingerDrawPath = new Path();
		mPaint = new Paint();

		mOffScreenPaint = new Paint();
		mOffScreenPaint.setStyle(Paint.Style.STROKE);
		mOffScreenPaint.setStrokeWidth(10);
		mOffScreenPaint.setColor(Color.BLACK);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(offScreenBitmap, 0, 0, mPaint);
		// canvas.drawPath(mFingerDrawPath, mPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		if (offScreenBitmap == null
				|| (offScreenBitmap.getWidth() != width || offScreenBitmap
						.getHeight() != height)) {
			Bitmap bm = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			offScreenCanvas = new Canvas(bm);
			if (offScreenBitmap != null) {
				offScreenCanvas.drawBitmap(offScreenBitmap, 0, 0, mPaint);
				offScreenBitmap.recycle();
			}
			offScreenBitmap = bm;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			isDowned = true;
			mOldX = x;
			mOldY = y;
			// mFingerDrawPath.moveTo(x, y);
			return true;
		case MotionEvent.ACTION_MOVE:
			if (isDowned) {
				// mFingerDrawPath.lineTo(x, y);
				if (offScreenCanvas != null) {
					offScreenCanvas.drawLine(mOldX, mOldY, x, y,
							mOffScreenPaint);
					mOldX = x;
					mOldY = y;
					invalidate();
				}
				return true;
			}
		case MotionEvent.ACTION_UP:
			isDowned = false;
			return true;
		}
		return super.onTouchEvent(event);
	}

}
