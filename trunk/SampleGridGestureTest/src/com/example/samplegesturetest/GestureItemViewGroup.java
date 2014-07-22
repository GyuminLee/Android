package com.example.samplegesturetest;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class GestureItemViewGroup extends FrameLayout implements
		GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener,
		OnSwipeListener {

	ArrayList<View> mClickView = new ArrayList<View>();

	public GestureItemViewGroup(Context context) {
		super(context);
	}

	public GestureItemViewGroup(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public GestureItemViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void registerChildClickView(View v) {
		mClickView.add(v);
	}

	public void unregisterChildClickView(View v) {
		mClickView.remove(v);
	}

	public void onChildViewClick(View v) {
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	int[] viewPointOnDisplay = new int[2];
	Rect viewRectOnDisplay = new Rect();
	
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		boolean bConsumed = false;
		for (View v : mClickView) {
//			v.getLocationOnScreen(viewPointOnDisplay);
//			viewRectOnDisplay.left = viewPointOnDisplay[0];
//			viewRectOnDisplay.top = viewPointOnDisplay[1];
//			viewRectOnDisplay.right = viewPointOnDisplay[0] + v.getMeasuredWidth();
//			viewRectOnDisplay.bottom = viewPointOnDisplay[1] + v.getMeasuredHeight();
			v.getGlobalVisibleRect(viewRectOnDisplay);
			if (viewRectOnDisplay.contains((int)e.getRawX(), (int)e.getRawY())) {
				onChildViewClick(v);
				bConsumed = true;
			}
		}
		return bConsumed;
	}
	
	public final boolean checkChildViewClick(MotionEvent e) {
		for (View v : mClickView) {
			v.getGlobalVisibleRect(viewRectOnDisplay);
			if (viewRectOnDisplay.contains((int)e.getRawX(), (int)e.getRawY())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onSwipe(View v, int orientation) {
		return false;
	}
}
