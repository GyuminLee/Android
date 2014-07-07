package com.example.samplecustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	private final static int MAX = 300;
	private final static int DELTA = 30;
	float[] points;
	
	public MyView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		mPaint = new Paint();
		int counts = MAX / DELTA + 1;
		points = new float[2 * 2 * counts];
		for (int i = 0; i < counts; i++) {
			points[i * 4] = 0;
			points[i * 4 + 1] = i * DELTA;
			points[i * 4 + 2] = MAX - i * DELTA;
			points[i * 4 + 3] = 0;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.RED);
		canvas.drawLines(points, mPaint);
	}

}
