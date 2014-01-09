package com.example.sample2offscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	Bitmap mDrawBitmap;
	Canvas mDrawCanvas;
	
	public MyView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
	}

	float oldX, oldY;
	boolean isDowned = false;
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		if (mDrawBitmap == null) {
			mDrawBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			mDrawCanvas = new Canvas(mDrawBitmap);
		}
		
		if (mDrawBitmap.getWidth() < width || mDrawBitmap.getHeight() < height) {
			Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bm);
			c.drawBitmap(mDrawBitmap, 0, 0, null);
			mDrawBitmap.recycle();
			mDrawBitmap = bm;
			mDrawCanvas = c;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			oldX = event.getX();
			oldY = event.getY();
			isDowned = true;
			return true;
		case MotionEvent.ACTION_MOVE :
			if (isDowned) {
				mDrawCanvas.drawLine(oldX, event.getX(), oldY, event.getY(), mPaint);
				oldX = event.getX();
				oldY = event.getY();
				invalidate();
				return true;
			}
		case MotionEvent.ACTION_UP :
			isDowned = false;
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(mDrawBitmap, 0, 0, null);
	}
}
