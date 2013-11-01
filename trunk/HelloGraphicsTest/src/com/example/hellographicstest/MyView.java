package com.example.hellographicstest;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	Bitmap mBitmap;
	Path mPath;
	Matrix mMatrix;
	float[] mPoints;
	GestureDetector mDetector;
	float radius;
	ScaleGestureDetector scaleDetector;

	Bitmap mOffScreenBitmap;
	Canvas mOffScreenCanvas;
	
	int mColor = 0xFF;
	
	public MyView(Context context) {
		super(context);
		init();
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint();
		InputStream is = getContext().getResources().openRawResource(
				R.raw.beach);
		mBitmap = BitmapFactory.decodeStream(is);
		mPath = new Path();
		mPath.addCircle(100, 100, 50, Path.Direction.CW);
		mMatrix = new Matrix();
		mPoints = new float[] { 0, 0, 25, 25, 50, 0, 75, 25, 100, 0, 0, 100,
				25, 200, 50, 100, 75, 125, 100, 100 };
		radius = 100;
		mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if (velocityX > 0) {
					mColor = mColor - 0xF;
					if (mColor < 0) mColor = 0xFF;
				} else {
					mColor = mColor + 0xF;
					if (mColor > 0xFF) mColor = 0;
				}
				invalidate();
				return true;
			}
			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				mColor = mColor - 0x0F;
				if (mColor < 0) {
					mColor = 0xFF;
				}
				invalidate();
				return true;
			}
		});
		scaleDetector = new ScaleGestureDetector(getContext(), new OnScaleGestureListener() {
			
			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				radius = radius * detector.getScaleFactor();
				if (radius < 50) {
					radius = 50;
				} else if (radius > 200) {
					radius = 200;
				}
				invalidate();
				return false;
			}
		});
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		requestLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int width = (210 > mBitmap.getWidth() + 10) ? 210
				: mBitmap.getWidth() + 10;
		int height = 210 + mBitmap.getHeight();

		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height, heightMeasureSpec));
		
		int measureWidth = getMeasuredWidth();
		int measureHeight = getMeasuredHeight();
		if (mOffScreenBitmap == null) {
			mOffScreenBitmap = Bitmap.createBitmap(measureWidth, measureHeight, Bitmap.Config.ARGB_8888);
			mOffScreenCanvas = new Canvas(mOffScreenBitmap);
		}
		if (mOffScreenBitmap.getWidth() != measureWidth || mOffScreenBitmap.getHeight() != measureHeight) {
			Bitmap bm = Bitmap.createBitmap(measureWidth, measureHeight, Bitmap.Config.ARGB_8888);
			Canvas cv = new Canvas(bm);
			cv.drawColor(Color.WHITE);
			cv.drawBitmap(mOffScreenBitmap, 0, 0, null);
			mOffScreenBitmap.recycle();
			mOffScreenBitmap = bm;
			mOffScreenCanvas = cv;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.WHITE);
		if (mOffScreenBitmap != null) {
			canvas.drawBitmap(mOffScreenBitmap, 0, 0, null);
		}
//		int color = Color.rgb(mColor, 0, 0);
//		mPaint.setColor(color);
//		canvas.drawCircle(200, 200, radius, mPaint);
		// // mPaint.setStyle(Paint.Style.STROKE);
		// mPaint.setStrokeWidth(10);
		// mPaint.setAntiAlias(true);
		// float[] intervals = {10 , 5 , 20 , 5};
		// Path arrow = new Path();
		// arrow.moveTo(0, 0);
		// arrow.lineTo(-5, 5);
		// arrow.lineTo(0, 5);
		// arrow.lineTo(5, 0);
		// arrow.lineTo(0, -5);
		// arrow.lineTo(-5, -5);
		//
		// PathEffect pathEffect = new PathDashPathEffect(arrow, 20, 0,
		// PathDashPathEffect.Style.MORPH);
		// mPaint.setPathEffect(pathEffect);

//		int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.RED };
//		float[] position = {0, 0.3f, 1};
//		Shader shader = new LinearGradient(75, 100, 125, 100, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
//		mPaint.setShader(shader);
//		canvas.drawCircle(100, 100, 75, mPaint);
		// mPaint.setColor(Color.BLUE);
		// mPaint.setTextSize(20);
		// // mPaint.setTextSkewX(1);
		//
		// // canvas.drawText("test", 10, 20, mPaint);
		// canvas.drawTextOnPath("TestTestTestTestTestTestTestTestTestTestTestTestTest",
		// mPath, 0, 0, mPaint);
		// // canvas.drawRect(10, 10, 200, 200, mPaint);

		// mMatrix.setScale(-1, 1, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
		// mMatrix.setSkew(1, 0);
//		ColorMatrix cm = new ColorMatrix();
//		cm.setSaturation(0);
//		ColorFilter cf = new ColorMatrixColorFilter(cm);
//		mPaint.setColorFilter(cf);
//		canvas.drawBitmap(mBitmap, 0,0, mPaint);
		// canvas.drawBitmapMesh(mBitmap, 4, 1, mPoints, 0, null, 0, mPaint);
	}

	float oldX, oldY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		scaleDetector.onTouchEvent(event);
		mDetector.onTouchEvent(event);
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			oldX = event.getX();
			oldY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE :
			float x = event.getX();
			float y = event.getY();
			mOffScreenCanvas.drawLine(oldX, oldY, x, y, mPaint);
			oldX = x;
			oldY = y;
			invalidate();
			break;
		}
		return true;
	}

}
