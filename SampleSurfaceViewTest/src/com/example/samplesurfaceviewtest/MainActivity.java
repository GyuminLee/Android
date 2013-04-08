package com.example.samplesurfaceviewtest;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceHolder mHolder;
	boolean isRunning = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SurfaceView sview = (SurfaceView)findViewById(R.id.surfaceView1);

		sview.getHolder().addCallback(this);
		sview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		isRunning = true;
		new DrawingThread().start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
		mHolder = holder;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHolder = holder;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHolder = null;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isRunning = false;
		super.onDestroy();
	}
	
	class DrawingThread extends Thread {
		
		Paint mPaint = new Paint();
		float startX, startY, stopX, stopY;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			startX = startY = 0;
			stopX = stopY = 100;
			mPaint.setColor(Color.RED);
			while(isRunning) {
				if (mHolder != null) {
					Canvas canvas = null;
					try {
						canvas = mHolder.lockCanvas();
						draw(canvas);
					} catch(Exception e) {
						e.printStackTrace();
					} finally {
						if (canvas != null) {
							mHolder.unlockCanvasAndPost(canvas);
						}
					}
				}
			}
		}
		
		public void draw(Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			startX+=1.0f;
			stopX-=1.0f;
			if (startX >= 100) {
				startX = 0;
			}
			if (stopX < 0) {
				stopX = 100;
			}
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
		}
		
	}

}
