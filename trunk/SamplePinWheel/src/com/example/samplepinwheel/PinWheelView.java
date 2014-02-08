package com.example.samplepinwheel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

public class PinWheelView extends View {

	public final static int CIRCLE_ANGLE = 360;
	public final static int SECOND_MILLIS = 1000;
	Bitmap mBitmap;
	Matrix mMatrix = new Matrix();
	float rps = 1.0f;
	int animationInterval = 50;
	float currentAngle;
	float angleGap = 18;
	
	public PinWheelView(Context context) {
		super(context);
	}

	public PinWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public PinWheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		requestLayout();
	}
	
	public void setRPS(float rps) {
		this.rps = rps;
		calculateAngleGap();
	}
	
	public float getRPS() {
		return rps;
	}
	
	private void calculateAngleGap() {
		angleGap = CIRCLE_ANGLE * rps / ((float)SECOND_MILLIS / (float)animationInterval);
	}
	
	public void setAnimationInterval(int interval) {
		animationInterval = interval;
		calculateAngleGap();
	}

	public int getAnimationInterval() {
		return animationInterval;
	}
	
	public void initializePinWheel() {
		currentAngle = 0;
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mBitmap != null) {
			int width = mBitmap.getWidth();
			int height = mBitmap.getHeight();
			setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	public void startPinWheel() {
		post(updateRunnable);
	}
	
	public void pausePinWheel() {
		removeCallbacks(updateRunnable);
	}
	
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			currentAngle = currentAngle + angleGap;
			if (currentAngle < 0) {
				currentAngle += CIRCLE_ANGLE;
			} else if (currentAngle > CIRCLE_ANGLE) {
				currentAngle -= CIRCLE_ANGLE;
			}
			invalidate();
			postDelayed(this, animationInterval);
		}
	};
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null) {
			mMatrix.setRotate(currentAngle, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
			canvas.drawBitmap(mBitmap, mMatrix, null);
		}
	}
	
}
