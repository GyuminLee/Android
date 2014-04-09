package com.example.sample3customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

public class MyView extends View {

	Paint mPaint = new Paint();
	
	public MyView(Context context) {
		super(context);
		init();
	}
	float[] mPoints;
	int count = 30;
	float maxWidth = 300;
	float margin = 20;
	Path mPath = new Path();
	String message = "Hello Android!";
	
	private void init() {
		mPoints = new float[(count + 1) * 2 * 2];
		float delta = maxWidth / count;
		int index = 0;
		for (float i = 0; i <= 300 ; i+= delta) {
			mPoints[index++] = margin;
			mPoints[index++] = margin + i;
			mPoints[index++] = margin + 300 - i;
			mPoints[index++] = margin;
 		}
		
		
		// arrow
//		mPath.moveTo(100, 100);
//		mPath.lineTo(50, 50);
//		mPath.lineTo(150, 50);
//		mPath.lineTo(200, 100);
//		mPath.lineTo(150, 150);
//		mPath.lineTo(50, 150);
		
		mPath.addCircle(100, 100, 100, Path.Direction.CW);
//		RectF rect = new RectF(100,100, 300,300);
//		float[] radii = {10, 5, 20, 10, 30, 15, 40, 20};
//		mPath.addRoundRect(rect, radii, Path.Direction.CW);
		
//		mPath.moveTo(0, 0);
//		mPath.cubicTo(100, 100, 200, 100, 100, 0);
//		mPath.quadTo(100, 100, 50, 200);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
// 		drawLine		
//		canvas.drawLines(mPoints, mPaint);
//		mPaint.setColor(Color.RED);
//		mPaint.setStrokeWidth(3);
//		canvas.drawPoints(mPoints, mPaint);
//		canvas.drawLine(0, 0, 200, 200, mPaint);
		
		// drawRect
//		mPaint.setStrokeWidth(50);
//		mPaint.setStyle(Paint.Style.FILL);
//		RectF rect = new RectF(100, 100, 300, 300);
//		canvas.drawRect(rect, mPaint);
//		canvas.drawRoundRect(rect, 100, 50, mPaint);
		
		// drawCircle
//		canvas.drawCircle(200, 200, 100, mPaint);
		
		// drawOval
//		RectF rect = new RectF(100,100, 300, 200);
//		canvas.drawOval(rect, mPaint);

		// drawArc
//		RectF rect = new RectF(100,100, 300, 300);
//		canvas.drawArc(rect, 45, 90, false, mPaint);
		
		// drawPath
//		mPaint.setStyle(Paint.Style.STROKE);
//		mPaint.setStrokeWidth(20);
//		mPaint.setStrokeCap(Paint.Cap.ROUND);
//		mPaint.setStrokeJoin(Paint.Join.ROUND);
//		canvas.drawPath(mPath, mPaint);
		
		// drawText
		mPaint.setTextSize(20);
//		mPaint.setTextSkewX(1);
		canvas.drawText(message, 0, 100, mPaint);
		canvas.drawTextOnPath(message, mPath, 0, 0, mPaint);
		
	}

}
