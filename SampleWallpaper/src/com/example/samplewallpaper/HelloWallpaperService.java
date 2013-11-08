package com.example.samplewallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;

public class HelloWallpaperService extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		return new MyWallPaperEngine();
	}

	class MyWallPaperEngine extends WallpaperService.Engine implements Runnable {
		
		boolean isRunning = false;
		boolean isHide = false;
		
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			isRunning = true;
			new Thread(this).start();
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			drawNotify();
		}
		
		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
			drawNotify();
		}
		

		@Override
		public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
			super.onSurfaceRedrawNeeded(holder);
			drawNotify();
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			isRunning = false;
			drawNotify();
		}
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			super.onVisibilityChanged(visible);
			isHide = !visible;
			drawNotify();
		}
		@Override
		public void run() {
			while(isRunning) {
				SurfaceHolder holder;
				Surface surface;
				while((holder=getSurfaceHolder()) == null || (surface = holder.getSurface()) == null || isHide) {
					drawWait();
					if (!isRunning) return;
				} 
				Canvas canvas = null;
				try {
					canvas = holder.lockCanvas();
					draw(canvas);
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					if (canvas != null) {
						holder.unlockCanvasAndPost(canvas);
					}
				}
				
			}
		}
		
		@Override
		public void onTouchEvent(MotionEvent event) {
			super.onTouchEvent(event);
		}
		
		int unitcolor = 0;
		
		private void draw(Canvas canvas) {
			unitcolor++;
			if (unitcolor > 255) {
				unitcolor = 0;
			}
			int color = Color.rgb(unitcolor, unitcolor, unitcolor);
			canvas.drawColor(color);
		}
		
		public synchronized void drawWait() {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public synchronized void drawNotify() {
			notify();
		}
		
	}

}
