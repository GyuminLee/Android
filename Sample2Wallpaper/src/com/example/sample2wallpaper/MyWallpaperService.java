package com.example.sample2wallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class MyWallpaperService extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		return new MyWallpaperEngine();
	}
	
	class MyWallpaperEngine extends WallpaperService.Engine implements Runnable {
		SurfaceHolder sh;
		boolean isRunning = true;
		boolean isVisible = false;
		
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			sh = surfaceHolder;
			new Thread(this).start();
			notifyDrawing();
		}
		
		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
			sh = holder;
			notifyDrawing();
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			sh = holder;
			notifyDrawing();
		}
		
		@Override
		public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
			super.onSurfaceRedrawNeeded(holder);
			sh = holder;
			notifyDrawing();
		}
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			super.onVisibilityChanged(visible);
			isVisible = visible;
			notifyDrawing();
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			isRunning = false;
		}

		private synchronized void notifyDrawing() {
			notify();
		}
		
		@Override
		public void run() {
			while(isRunning) {
				if (!isVisible || sh == null || sh.getSurface() == null) {
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					continue;
				}
				Canvas canvas = null;
				
				try {
					canvas = sh.lockCanvas();
					draw(canvas);
				} finally {
					if (canvas != null) {
						sh.unlockCanvasAndPost(canvas);
					}
				}
				
			}
		}

		Paint mPaint = new Paint();
		
		int mX = 0;
		private void draw(Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			mPaint.setColor(Color.BLUE);
			mPaint.setTextSize(30);
			canvas.drawText("Hello Wallpaper", mX, 100, mPaint);
			mX++;
			if (mX > 200) {
				mX = 0;
			}
		}
	}

}
