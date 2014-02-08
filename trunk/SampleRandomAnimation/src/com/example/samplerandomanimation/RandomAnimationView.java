package com.example.samplerandomanimation;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

public class RandomAnimationView extends View {

	int positionX = 0;
	int positionY = 0;
	boolean positiveX = true;
	boolean positiveY = true;
	int maxMovementX = 15;
	int maxMovementY = 15;
	int minMovementX = 5;
	int minMovementY = 5;
	Bitmap mBitmap;
	int animationInterval = 50;
	Matrix mMatrix = new Matrix();
	Random random = new Random(System.currentTimeMillis());

	public RandomAnimationView(Context context) {
		super(context);
	}

	public RandomAnimationView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public RandomAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setMovement(int minX, int maxX, int minY, int maxY) {
		minMovementX = minX;
		maxMovementX = maxX;
		minMovementY = minY;
		maxMovementY = maxY;
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	public void setAnimationInterval(int interval) {
		animationInterval = interval;
	}

	public void startAnimation() {
		post(update);
	}

	public void pauseAnimation() {
		removeCallbacks(update);
	}

	public void initializeAnimation() {
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		if (mBitmap != null && width > mBitmap.getWidth()
				&& height > mBitmap.getHeight()) {
			positionX = (width - mBitmap.getWidth()) / 2;
			positionY = (height - mBitmap.getHeight()) / 2;
		} else {
			positionX = 0;
			positionY = 0;
		}
		positiveX = true;
		positiveY = true;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null) {
			canvas.drawColor(Color.WHITE);
			mMatrix.setTranslate(positionX, positionY);
			canvas.drawBitmap(mBitmap, mMatrix, null);
		}
	}

	Runnable update = new Runnable() {

		@Override
		public void run() {
			int width = getMeasuredWidth();
			int height = getMeasuredHeight();
			if (mBitmap != null && width > mBitmap.getWidth()
					&& height > mBitmap.getHeight()) {
				int randX = Math
						.round((float) (random.nextFloat() * (maxMovementX - minMovementX)))
						+ minMovementX;
				int randY = Math
						.round((float) (random.nextFloat() * (maxMovementY - minMovementY)))
						+ minMovementY;
				positionX += (positiveX) ? randX : -randX;
				positionY += (positiveY) ? randY : -randY;

				if (positionX < 0) {
					positiveX = true;
					positionX = 0;
				} else if (positionX > width - mBitmap.getWidth()) {
					positiveX = false;
					positionX = width - mBitmap.getWidth();
				}

				if (positionY < 0) {
					positiveY = true;
					positionY = 0;
				} else if (positionY > height - mBitmap.getHeight()) {
					positiveY = false;
					positionY = height - mBitmap.getHeight();
				}

				invalidate();
			}
			postDelayed(this, animationInterval);
		}
	};

}
