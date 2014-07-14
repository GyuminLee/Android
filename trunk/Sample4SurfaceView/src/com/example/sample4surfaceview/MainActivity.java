package com.example.sample4surfaceview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity implements SurfaceHolder.Callback,
		Runnable {

	SurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceView.getHolder().addCallback(this);
		startX = 0;
		startY = 0;
		endX = MAX_LENGTH;
		endY = 0;
		new Thread(this).start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSurface = holder.getSurface();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mSurface = holder.getSurface();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurface = null;
	}

	Surface mSurface = null;
	boolean isRunning = true;

	Paint mPaint = new Paint();
	int startX, startY, endX, endY;
	private static final int MAX_LENGTH = 300;
	private static final int DELTA = 3;
	
	@Override
	public void run() {
		while (isRunning) {
			try {
				if (mSurface != null) {
					Canvas canvas = null;
					try {
						canvas = mSurface.lockCanvas(null);
						draw(canvas);
					} catch(Exception e) {
						
					} finally {
						if (canvas != null) {
							mSurface.unlockCanvasAndPost(canvas);
						}
					}
					
				}
			} catch (Exception e) {

			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void draw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.RED);
		canvas.drawLine(startX, startY, endX, endY, mPaint);
		
		startX = 0;
		startY += DELTA;
		if (startY > MAX_LENGTH) {
			startY = 0;
		}
		endX -= DELTA;
		if (endX < 0) {
			endX = MAX_LENGTH;
		}
		endY = 0;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRunning = false;
	}
}
