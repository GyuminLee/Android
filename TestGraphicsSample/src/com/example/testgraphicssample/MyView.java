package com.example.testgraphicssample;

import java.io.InputStream;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class MyView extends View {

	Paint mPaint = new Paint();
	
	Path mPath = new Path();
	
	Bitmap mBitmap;
	
	Bitmap drawingBitmap;
	Canvas mOffCanvas;
	
	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);
		int posx = ta.getInt(R.styleable.MyView_posx, -1);
		int posy = ta.getInt(R.styleable.MyView_posy, -1);
		String drawText = ta.getString(R.styleable.MyView_drawText);
		int bgColor = ta.getColor(R.styleable.MyView_bgColor, Color.BLACK);
		
		init(context);
	}


	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);

	}
	
	public void setData(String data) {
		
	}
	void init(Context context) {
//		mPath.moveTo(50, 50);
//		mPath.lineTo(100, 100);
//		mPath.lineTo(50, 150);
//		mPath.lineTo(0, 100);
		
//		mPath.addCircle(100, 100, 50, Path.Direction.CW);
		
		InputStream is = context.getResources().openRawResource(R.drawable.gallery_photo_1);
		
		mBitmap = BitmapFactory.decodeStream(is);
		
//		Bitmap bm = Bitmap.createBitmap(mBitmap, 10, 10, 20, 20);
		
		Bitmap bm2 = Bitmap.createScaledBitmap(mBitmap, 100, 100, false);
		mBitmap.recycle();
		
		mBitmap = bm2;
		
		drawingBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
		mOffCanvas = new Canvas(drawingBitmap);
		
		mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
				
				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						float velocityY) {
					// TODO Auto-generated method stub
					// ....
					
					return true;
				}
				
			});
		
		mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {

				@Override
				public boolean onScale(ScaleGestureDetector detector) {
					// TODO Auto-generated method stub
					detector.getScaleFactor();
					return super.onScale(detector);
				}
				
			});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		mPaint.setColor(Color.RED);

		canvas.drawBitmap(drawingBitmap, 0,0, mPaint);
//		mPaint.setTextSize(20);

//		ColorMatrix colorMatrix = new ColorMatrix();
//		colorMatrix.setSaturation(0);
//		
//		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
//		mPaint.setColorFilter(filter);
//		
//		mPaint.setAntiAlias(true);
//		
//		Matrix matrix = new Matrix();
//		
//		matrix.setScale(-1, 1, 50, 50);
//		matrix.postRotate(30, 50, 50);
//		matrix.postTranslate(100, 100);
		
		canvas.drawColor(Color.WHITE);
		
		canvas.drawBitmap(mBitmap, 100, 100, mPaint);

		
		
		Rect r = new Rect();
		r.left = r.top = 100;
		r.bottom = r.right = 200;
		
		AvoidXfermode xfer = new AvoidXfermode(Color.BLUE, 224, AvoidXfermode.Mode.TARGET);
		mPaint.setXfermode(xfer);
		
		canvas.drawRect(r, mPaint);
		
//		canvas.drawBitmap(mBitmap, matrix, mPaint);
		
		
//		canvas.drawLine(10, 10, 110, 110, mPaint);
		
//		mPaint.setStyle(Paint.Style.STROKE);
//		mPaint.setStrokeWidth(10);
//		
//		RectF r = new RectF();
//		r.left = 10;
//		r.top = 10;
//		r.right = 210;
//		r.bottom = 110;
////		canvas.drawRect(r, mPaint);
//		
////		canvas.drawRoundRect(r, 5, 5, mPaint);
//		
//		canvas.drawOval(r, mPaint);
//		
//		canvas.drawCircle(60, 160, 50, mPaint);
//		
//		canvas.drawPath(mPath, mPaint);
		
//		canvas.drawText("Hello g Android", 100, 150, mPaint);
		
//		canvas.drawTextOnPath("Hello g Android", mPath, 0, 0, mPaint);
		
		
	}
	
	
	GestureDetector mDetector;
	
	ScaleGestureDetector mScaleDetector;	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (mDetector.onTouchEvent(event)) {
			return true;
		}
		mScaleDetector.onTouchEvent(event);
		super.onTouchEvent(event);
		
		return true;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
