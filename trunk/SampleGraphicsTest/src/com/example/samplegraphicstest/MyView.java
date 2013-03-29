package com.example.samplegraphicstest;

import java.io.InputStream;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathDashPathEffect.Style;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	Path mPath;
	Bitmap mBitmap;
	int mBgColor = Color.WHITE;
	
	float[] mPoints = { 
			0 , 0 ,  25, 25 ,  50, 0 ,  75, 25 ,   100, 0 , 
			0 , 100, 25, 125 , 50, 100, 75, 125,   100, 100
	};
	
	
	Path mPath2;
	
	String text = "hello android! hello android! hello android! hello android! hello android!";
	
	public MyView(Context context) {
		super(context);
		init(context);
	}
	
	

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}



	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mPath = new Path();
		mPath.addCircle(200, 200, 100, Path.Direction.CW);
		
		InputStream is = context.getResources().openRawResource(R.raw.gallery_photo_1);
		mBitmap = BitmapFactory.decodeStream(is);

		Bitmap scaleBitmap = Bitmap.createScaledBitmap(mBitmap, 100, 100, false);
		mBitmap.recycle();

		mBitmap = scaleBitmap;
		
		mPath2 = new Path();
		mPath2.moveTo(0, 0);
		mPath2.lineTo(-5, 5);
		mPath2.lineTo(5, 5);
		mPath2.lineTo(10, 0);
		mPath2.lineTo(5, -5);
		mPath2.lineTo(-5, -5);
		mPath2.lineTo(0, 0);		
	}

	public void setBGColor(int color) {
		mBgColor = color;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(mBgColor);
		canvas.drawBitmap(mBitmap, 10,  10, mPaint);

//		AvoidXfermode xfer = new AvoidXfermode(Color.BLUE, 192, AvoidXfermode.Mode.TARGET);
//		mPaint.setXfermode(xfer);
//		mPaint.setColor(Color.RED);
//		canvas.drawRect(10, 10, 110, 110, mPaint);
		
//		ColorMatrix cm = new ColorMatrix();
//		cm.setSaturation(0);
//		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);
//		float[] m = { -1, 0, 0, 0, 255, 
//					   0, -1, 0, 0, 255,
//					   0, 0, -1, 0, 255,
//					   0, 0, 0, 1, 0};
//		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(m);
//		ColorMatrix matrixA = new ColorMatrix();
//		ColorMatrix matrixB = new ColorMatrix();
//		matrixA.setSaturation(0);
//		matrixB.setScale(1.0f, 0.95f, 0.82f, 1.0f);
//		matrixA.setConcat(matrixB, matrixA);
//		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrixA);
//		mPaint.setColorFilter(filter);
//		canvas.drawBitmap(mBitmap, 10, 200, mPaint);
//		mPaint.setColor(Color.BLUE);
//		mPaint.setAlpha(0x80);
//		int[] colors = {0xFFFFFFFF, 0x00FFFFFF};
//		LinearGradient linear = new LinearGradient(10, 10, 110, 110, colors, null, Shader.TileMode.CLAMP);
//		mPaint.setShader(linear);
//		mPaint.setStyle(Paint.Style.FILL);
//		mPaint.setStrokeWidth(10);
//	
//		float[] intervals = {10, 5, 20, 5 };
//		float phase = 0;
//		DashPathEffect effect = new DashPathEffect(intervals, phase);
//		PathDashPathEffect effect = new PathDashPathEffect(mPath2, 20, 0, PathDashPathEffect.Style.TRANSLATE);
//		mPaint.setPathEffect(effect);
//		mPaint.setShadowLayer(5, 5, 5, Color.DKGRAY);
//		canvas.drawCircle(150, 150, 100, mPaint);
//		
//		mPaint.setColor(Color.RED);
//		canvas.drawCircle(100, 100, 50, mPaint);
//		
//		mPaint.setColor(Color.BLACK);
//		mPaint.setTextSize(10);
//		canvas.drawTextOnPath(text, mPath, 0, 0, mPaint);
//		
//		canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//		
//		Matrix m = new Matrix();
//		m.setScale(-1, 1, 50, 50);
//		m.postTranslate(200, 200);
//		m.postRotate(45);
//		canvas.drawBitmap(mBitmap, m, mPaint);
//		canvas.drawBitmapMesh(mBitmap, 4, 1, mPoints, 0, null, 0, mPaint);
		
	}
}
