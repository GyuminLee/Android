package com.example.testpatheffectsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	Path mArrowPath;
	
	public MyView(Context context) {
		super(context);
		mPaint = new Paint();
//		mPaint.setStyle(Paint.Style.STROKE);
//		mPaint.setStrokeWidth(10);
		mPaint.setColor(Color.RED);
//		float[] intervals = { 10 , 5, 20, 5 }; 
//		mArrowPath = new Path();
//		mArrowPath.moveTo(0, 0);
//		mArrowPath.lineTo(-5, 5);
//		mArrowPath.lineTo(0, 5);
//		mArrowPath.lineTo(5, 0);
//		mArrowPath.lineTo(0, -5);
//		mArrowPath.lineTo(-5, -5);
//		
//		PathDashPathEffect pdpe = new PathDashPathEffect(mArrowPath, 20, 0, PathDashPathEffect.Style.MORPH);
//		mPaint.setPathEffect(pdpe);

		mPaint.setShadowLayer(10, 10, 10, Color.DKGRAY);
		
//		DashPathEffect dpe = new DashPathEffect(intervals, 0);
//		mPaint.setPathEffect(dpe);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.drawCircle(210, 210, 100, mPaint);
//		canvas.drawRect(10, 10, 210, 210, mPaint);
	}

}
