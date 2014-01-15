package com.example.sample2surfaceview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView surfaceView;

	Surface surface = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceView.getHolder().addCallback(this);
		new Thread(animation).start();
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
		surface = holder.getSurface();
		synchronized (animation) {
			animation.notify();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		surface = holder.getSurface();
		synchronized (animation) {
			animation.notify();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		surface = null;
	}

	boolean isRunning = true;

	Runnable animation = new Runnable() {

		@Override
		public void run() {
			while (isRunning) {
				while (surface == null) {
					synchronized (animation) {
						try {
							animation.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				Canvas canvas = null;
				try {
					canvas = surface.lockCanvas(null);
					draw(canvas);
				} finally {
					if (canvas != null) {
						surface.unlockCanvasAndPost(canvas);
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
		
		int x1 = 0 ,y1 = 0, x2 = 300, y2 = 0;
		int delta = 30;
		
		Paint mPaint = new Paint();
		private void draw(Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			mPaint.setColor(Color.RED);
			mPaint.setStrokeWidth(5);
			canvas.drawLine(x1, y1, x2, y2, mPaint);
			y1 += delta;
			if (y1 > 300) {
				y1 = 0;
			}
			x2 -= delta;
			if (x2 < 0) {
				x2 = 300;
			}
		}
	};

}
