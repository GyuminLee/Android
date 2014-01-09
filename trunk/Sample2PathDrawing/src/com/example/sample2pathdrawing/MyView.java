package com.example.sample2pathdrawing;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	public static class Item {
		Paint mPaint;
		Path mPath;
	}
	
	Paint mPaint;
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	public MyView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPaint = new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		for (int i = 0 ; i < items.size(); i++) {
			Item item = items.get(i);
			canvas.drawPath(item.mPath, item.mPaint);
		}
	}
	
	Item current;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			current = new Item();
			current.mPaint = new Paint(mPaint);
			current.mPath = new Path();
			current.mPath.moveTo(event.getX(), event.getY());
			items.add(current);
			return true;
		case MotionEvent.ACTION_MOVE :
			current.mPath.lineTo(event.getX(), event.getY());
			invalidate();
			return true;
		case MotionEvent.ACTION_UP :
			current = null;
			return true;
		}
		return super.onTouchEvent(event);
	}
	
}
