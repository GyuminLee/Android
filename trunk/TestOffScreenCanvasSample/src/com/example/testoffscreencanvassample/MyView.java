package com.example.testoffscreencanvassample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	Bitmap mDrawBitmap;
	Canvas mOffCanvas;
	Paint mOffPaint;
	
	public MyView(Context context) {
		super(context);
		mOffPaint = new Paint();
		mOffPaint.setColor(Color.RED);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mDrawBitmap == null) {
			mDrawBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
			mOffCanvas = new Canvas(mDrawBitmap);
		}
		
		if (mDrawBitmap.getWidth() != getMeasuredWidth() || mDrawBitmap.getHeight() != getMeasuredHeight()) {
			Bitmap nBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
			Canvas nCanvas = new Canvas(nBitmap);
			nCanvas.drawBitmap(mDrawBitmap, 0, 0, null);
			mDrawBitmap = nBitmap;
			mOffCanvas = nCanvas;
		}
		
		canvas.drawBitmap(mDrawBitmap, 0, 0, null);
	}
	
	boolean isPressed = false;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			isPressed = true;
			drawPoint(event.getX(), event.getY());
			return true;
		case MotionEvent.ACTION_MOVE :
			if (isPressed) {
				drawPoint(event.getX(), event.getY());
				return true;
			}
			break;
		case MotionEvent.ACTION_UP :
			isPressed = false;
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	private void drawPoint(float cx, float cy) {
		mOffCanvas.drawCircle(cx, cy, 5, mOffPaint);
		invalidate();
	}
}
