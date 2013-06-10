package com.example.testsurfaceviewsample2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MyDrawing {

	SurfaceHolder mHolder;
	boolean isRunning = false;
	
	int startX = 10;
	int startY = 10;
	int endX = 210;
	int endY = 210;
	int delta = 2;
	Paint mPaint;
	
	public MyDrawing() {
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
	}
	
	public void setSurfaceHolder(SurfaceHolder holder) {
		mHolder = holder;
	}
	
	public void start() {

		if (isRunning) return;
		
		isRunning = true;
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				while(isRunning && mHolder != null) {
					Canvas canvas = null;
					try {
						canvas = mHolder.lockCanvas();
						draw(canvas);
					} finally {
						if (canvas != null) {
							mHolder.unlockCanvasAndPost(canvas);
						}
					}
				}
			}
		}).start();
	}
	
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.drawLine(startX, startY, endX, endY, mPaint);
		startX+=delta;
		if (startX > 210) {
			startX = 10;
		}
		endX -= delta;
		if (endX < 10) {
			endX = 210;
		}
	}
	
	public void stop() {
		isRunning = false;
	}
}
