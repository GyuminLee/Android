package com.example.samplegesturetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

public class MyView extends View {

	Bitmap mBitmap;
	Matrix mMatrix;
	Paint mPaint;
	
	GestureDetector mDetector;
	ScaleGestureDetector mScaleDetector;
	float mFactor = 1.0f;
	public static final float MAX_SCALE_VALUE = 3.0f;
	public static final float MIN_SCALE_VALUE = 0.5f;
	
	public MyView(Context context) {
		this(context,null,0);
	}

	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mBitmap = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.gallery_photo_1)).getBitmap();
		mMatrix = new Matrix();
		mMatrix.reset();
		mPaint = new Paint();
		mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "on Fling... vx : " + velocityX + ", vy : " + velocityY, 
						Toast.LENGTH_SHORT).show();
				post(new UpdateRunnable(velocityX, velocityY));
				return true;
			}
		});
		
		mScaleDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener(){
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				mFactor *= detector.getScaleFactor();
				mFactor = adjustScaleFactor(mFactor);
				
				mMatrix.setScale(mFactor, mFactor);
				invalidate();
				return true;
			}
		});
	}
	
	private float adjustScaleFactor(float factor) {
		if (factor < MIN_SCALE_VALUE) {
			return MIN_SCALE_VALUE;
		} else if (factor > MAX_SCALE_VALUE) {
			return MAX_SCALE_VALUE;
		} else {
			return factor;
		}
	}
	
	class UpdateRunnable implements Runnable {

		float vx;
		float vy;

		public UpdateRunnable(float vx, float vy) {
			this.vx = vx;
			this.vy = vy;
			mCamera = new Camera();
		}
		
		Camera mCamera;
		int count = 0;
		public final static int MAX_COUNT = 10;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			float ry = vx / 100 / MAX_COUNT * count;
			float rx = vy / 100 / MAX_COUNT * count;
			
			mCamera.save();
			
			mCamera.rotateX(rx);
			mCamera.rotateY(ry);
			mCamera.getMatrix(mMatrix);
			mCamera.restore();

			mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight()/2);
			mMatrix.postTranslate(mBitmap.getWidth()/ 2, mBitmap.getHeight() / 2);
			invalidate();
			
			count++;
			if (count <= MAX_COUNT) {
				postDelayed(this, 100);
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
		
		super.onDraw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//mDetector.onTouchEvent(event);
		mScaleDetector.onTouchEvent(event);
		return true;
	}
	

}
