package com.example.sample2customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	Path mPath;
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
	
	private void init() {
		mPaint = new Paint();
		Resources res = getContext().getResources();
		drawWidth = res.getDimension(R.dimen.my_draw_width);
		drawHeight = res.getDimension(R.dimen.my_draw_height);
		mPath = new Path();
		mPath.addCircle(200, 200, 100, Path.Direction.CW);
//		mPath.moveTo(50, 50);
//		mPath.cubicTo(100, 75, 75, 100, 60, 60);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(10);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeMiter(10);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
//		canvas.drawPoints(points, mPaint);
		canvas.save();
		canvas.translate(100, 100);
		canvas.rotate(45, 100, 100);
		canvas.drawLines(points, mPaint);
		canvas.restore();
//		mPaint.setAntiAlias(true);
//		canvas.drawCircle(200, 200, 100, mPaint);
		RectF oval = new RectF(100,100,300,200);
//		canvas.drawArc(oval, 45, 45, false, mPaint);
//		canvas.drawRoundRect(oval, 20, 30, mPaint);
//		canvas.drawPath(mPath, mPaint);
	}

}
