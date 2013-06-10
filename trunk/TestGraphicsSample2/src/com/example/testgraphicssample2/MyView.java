package com.example.testgraphicssample2;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

public class MyView extends View {

	Paint mPaint;
	String mHello = "Hello Android!";
	Path mPath;
	Bitmap mBitmap;
	Matrix mMatrix;
	float[] vertics = { 10, 210, 60, 260, 110, 210, 160, 260, 210, 210, 
						10, 310, 60, 360, 110, 310, 160, 360, 210, 310 };
	
	GestureDetector mDetector;
	ScaleGestureDetector mScaleDetector;
	float mScaleFactor = 1.0f;
	
	
	public MyView(Context context) {
		super(context);
		init(context);
	}

	
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}


	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width, height;
		
		width = mBitmap.getWidth();
		height = 310 + mBitmap.getHeight();
		
		switch(widthMode) {
		case MeasureSpec.AT_MOST :
			if (width > widthSize) {
				width = widthSize;
			}
			break;
		case MeasureSpec.EXACTLY :
			width = widthSize;
			break;
		case MeasureSpec.UNSPECIFIED :
			break;
		}
		
		switch(heightMode) {
		case MeasureSpec.AT_MOST :
			if (height > heightSize) {
				height = heightSize;
			}
			break;
		case MeasureSpec.EXACTLY :
			height = heightSize;
			break;
		case MeasureSpec.UNSPECIFIED :
			break;
		}
		
		setMeasuredDimension(width, height);
	}

	
	public void setText(String text) {
		mHello = text;
	}
	
	private void init(Context context) {
		mPaint = new Paint();
//		int color = Color.rgb(0xFF, 0x00, 0x00);
//		mPaint.setColor(color);
////		mPaint.setAlpha(0x80);
//		mPaint.setTextSize(40);
//		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//		int[] colors = {Color.RED, Color.YELLOW, Color.BLUE };
//		float[] positions = {0.0f, 0.3f, 1.0f};
////		LinearGradient lg = new LinearGradient(60, 60, 160, 60,colors, positions, Shader.TileMode.MIRROR);
//		RadialGradient rg = new RadialGradient(110, 110, 100, Color.alpha(0xFF), Color.alpha(0x80), Shader.TileMode.CLAMP);
//		int[] sColors = {Color.RED, Color.YELLOW, Color.BLUE, Color.RED };
//		SweepGradient sg = new SweepGradient(110, 110, sColors, null);
//		ComposeShader cs = new ComposeShader(sg, rg, PorterDuff.Mode.ADD);
//		mPaint.setShader(cs);
//
////		
////		mPath = new Path();
////		mPath.addCircle(210, 160, 100, Path.Direction.CW);
////		
		InputStream is = context.getResources().openRawResource(R.raw.gallery_photo_1);
		mBitmap = BitmapFactory.decodeStream(is);
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
		mPaint.setColorFilter(cf);
////		mMatrix = new Matrix();
////		mMatrix.setScale(1, -1, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
////		mMatrix.postSkew(1, 0);
////		mMatrix.postTranslate(100, 100);
		
		mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				Toast.makeText(getContext(), "onFling : " + velocityX + "," + velocityY, Toast.LENGTH_SHORT).show();
				return true;
			}
			
		});
		mScaleDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {

			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				mScaleFactor *= detector.getScaleFactor();
				return true;
			}
			
		});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		int w = getMeasuredWidth();
		int h = getMeasuredHeight();
		canvas.drawBitmap(mBitmap, 0, 210, null);
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
		
//		canvas.drawCircle(110, 110, 100, mPaint);
//		canvas.drawRect(10, 10, 210, 210, mPaint);
//		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
//		canvas.drawBitmap(mBitmap, 10, 310, mPaint);
//		canvas.save();
//		canvas.rotate(45, 160, 260);
//		canvas.drawBitmapMesh(mBitmap, 4, 1, vertics, 0, null, 0, mPaint);
//		canvas.restore();
//		canvas.drawText(mHello, 10, 110, mPaint);
//		canvas.drawRect(10, 10, 210, 210, mPaint);
//		canvas.drawText(mHello, 10, 250, mPaint);
//		canvas.drawPath(mPath, mPaint);
//		canvas.drawTextOnPath(mHello, mPath, 0, 0, mPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		mScaleDetector.onTouchEvent(event);
		return true;
	}
}
