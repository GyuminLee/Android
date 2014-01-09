package com.example.sample2customview;

import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	Path mPath;
	Path mCursorPath;
	float drawWidth;
	float drawHeight;
	float[] points = { 10 , 10 , 
						60, 110,
						60, 110,
						110, 10,
						110, 10,
						160 , 110,
						160 , 110,
						210 , 10 };
	
	public MyView(Context context) {
		super(context);
		init();
	}
	
	Bitmap mBitmap;
	Matrix mMatrix;
	float[] mashPoint = {0, 200, 50, 250, 100, 200, 150, 250, 200 , 200 ,
						 0, 300, 50, 500, 100, 300, 150, 350, 200, 300
	};
	
	private void init() {
		mPaint = new Paint();
		Resources res = getContext().getResources();
		drawWidth = res.getDimension(R.dimen.my_draw_width);
		drawHeight = res.getDimension(R.dimen.my_draw_height);
		InputStream is = res.openRawResource(R.drawable.gallery_photo_1);
		mBitmap = BitmapFactory.decodeStream(is);
		mMatrix = new Matrix();
		mMatrix.reset();
//		Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmap, 100, 100, false);
//		mBitmap.recycle();
//		mBitmap = scaledBitmap;
		
		mPath = new Path();
//		mPath.moveTo(0, 0);
//		mPath.lineTo(50, 50);
//		mPath.lineTo(100, 0);
//		mPath.lineTo(150, 50);
//		mPath.lineTo(200, 0);
		mPath.addCircle(200, 200, 100, Path.Direction.CCW);
//		mPath.moveTo(50, 50);
//		mPath.cubicTo(100, 75, 75, 100, 60, 60);
		mCursorPath = new Path();
		mCursorPath.moveTo(0, 0);
		mCursorPath.lineTo(-5, 5);
		mCursorPath.lineTo(0, 5);
		mCursorPath.lineTo(5, 0);
		mCursorPath.lineTo(0, -5);
		mCursorPath.lineTo(-5, -5);
		
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
		canvas.drawBitmap(mBitmap, 0, 0,mPaint);
		
		canvas.drawBitmapMesh(mBitmap, 4, 1, mashPoint, 0, null, 0, mPaint);
		
//		mMatrix.setScale(1, -1, 0, mBitmap.getHeight());
//		mMatrix.postSkew(0.5f, 0, 0, mBitmap.getHeight());
//		mMatrix.setRotate(45, 0, mBitmap.getHeight());
//		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
//		canvas.drawBitmap(mBitmap, 100, 100, mPaint);
//		mPaint.setStyle(Paint.Style.STROKE);
//		mPaint.setStrokeWidth(10);
//		CornerPathEffect pe = new CornerPathEffect(10);
//		mPaint.setPathEffect(pe);
//		float[] intervals = {10, 5, 20, 5};
//		DashPathEffect dpe = new DashPathEffect(intervals, 15);
//		PathDashPathEffect pdpe = new PathDashPathEffect(mCursorPath, 10, -5, PathDashPathEffect.Style.MORPH);
//		mPaint.setPathEffect(pdpe);
//		canvas.drawLine(100, 100, 300, 300, mPaint);
//		canvas.drawPath(mPath, mPaint);
//		canvas.drawCircle(200, 200, 100, mPaint);
//		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
//		mPaint.setStyle(Paint.Style.STROKE);
//		mPaint.setStrokeWidth(10);
//		mPaint.setStrokeJoin(Paint.Join.ROUND);
//		mPaint.setStrokeMiter(10);
//		mPaint.setStrokeCap(Paint.Cap.ROUND);
//		canvas.drawPoints(points, mPaint);
//		canvas.save();
//		canvas.translate(100, 100);
//		canvas.rotate(45, 100, 100);
//		canvas.drawLines(points, mPaint);
//		canvas.restore();
//		mPaint.setAntiAlias(true);
//		canvas.drawCircle(200, 200, 100, mPaint);
//		RectF oval = new RectF(100,100,300,200);
//		canvas.drawArc(oval, 45, 45, false, mPaint);
//		canvas.drawRoundRect(oval, 20, 30, mPaint);
//		canvas.drawPath(mPath, mPaint);
//		mPaint.setTextSize(40);
//		mPaint.setTextSkewX(0.5f);
//		canvas.drawText("Hello Android g", 0, 100, mPaint);
//		canvas.drawTextOnPath("Hello Android", mPath, 0, 0, mPaint);
	}

}
