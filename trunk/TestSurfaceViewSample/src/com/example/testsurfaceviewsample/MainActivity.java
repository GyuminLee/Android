package com.example.testsurfaceviewsample;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity implements SurfaceHolder.Callback, Runnable {

	SurfaceView surfaceView;
	
	SurfaceHolder mHolder;
	
	boolean isDrawing = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
		surfaceView.getHolder().addCallback(this);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		isDrawing = true;
		new Thread(this).start();		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isDrawing = false;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		mHolder = arg0;
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mHolder = arg0;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		if (mHolder == arg0) {
			mHolder = null;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isDrawing) {
			
			if (mHolder != null) {
				Canvas canvas = null;
			
				try {
					canvas = mHolder.lockCanvas();
					backgroundDraw(canvas);
				} catch(Exception e) {
					
				} finally {
					if (canvas != null) {
						mHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
			
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	Paint mPaint = new Paint();
	int x0 = 0, y0 = 0, x1 = 200, y1 = 200;
	
	private void backgroundDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		x0 = (x0 + 10) % 200;
		x1 = (x1 - 10);
		if (x1 < 0) {
			x1 = 200;
		}
		
		mPaint.setColor(Color.RED);
		
		canvas.drawColor(Color.WHITE);
		
		canvas.drawLine(x0, y0, x1, y1, mPaint);
	}

}
