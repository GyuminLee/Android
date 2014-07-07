package com.example.sample4offscreencanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

	Bitmap undoBitmap;
	
	public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DrawingView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mOffScreenPaint = new Paint();
		mPaint = new Paint();
	}
	
	Bitmap offScreenBitmap;
	Canvas offScreenCanvas;
	Paint mOffScreenPaint;
	Paint mPaint;
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed) {
			int width = right - left - getPaddingLeft() - getPaddingRight();
			int height = bottom - top - getPaddingTop() - getPaddingBottom();
			Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bm);
			if (offScreenBitmap != null) {
				c.drawBitmap(offScreenBitmap, 0, 0, mPaint);
				offScreenBitmap.recycle();
			}
			
			offScreenBitmap = bm;
			offScreenCanvas = c;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (offScreenBitmap != null) {
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(offScreenBitmap, 0, 0, mPaint);
		}
	}

	
	public void setStrokeWidth(float width) {
		mOffScreenPaint.setStrokeWidth(width);
	}
	
	public void undo() {
		if (undoBitmap != null) {
			offScreenCanvas.drawColor(Color.WHITE);
			offScreenCanvas.drawBitmap(undoBitmap, 0, 0, mPaint);
			undoBitmap.recycle();
			undoBitmap = null;
			invalidate();
		}
	}
	
	float oldX,oldY;
	boolean isPressed = false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			oldX = event.getX();
			oldY = event.getY();
			isPressed = true;
			if (undoBitmap != null) {
				undoBitmap.recycle();
				undoBitmap = null;
			}
			undoBitmap = Bitmap.createBitmap(offScreenBitmap);
			return true;
		case MotionEvent.ACTION_MOVE :
			if (isPressed && offScreenCanvas != null) {
				float x = event.getX();
				float y = event.getY();
				offScreenCanvas.drawLine(oldX, oldY, x, y, mOffScreenPaint);
				invalidate();
				oldX = x;
				oldY = y;
			}
			return true;
		case MotionEvent.ACTION_UP :
			isPressed = false;
			return true;
		}
		return super.onTouchEvent(event);
	}
}
