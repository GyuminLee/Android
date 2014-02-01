package com.begentgroup.draganddrop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.begentgroup.draganddrop.DragController;
import com.begentgroup.draganddrop.DragSource;

public class DragAndDropLayout extends LinearLayout implements DragSource {

	DragController mController;
	public DragAndDropLayout(Context context) {
		super(context);
	}

	public DragAndDropLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DragAndDropLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean allowDrag() {
		return true;
	}

	@Override
	public void setDragController(DragController dragger) {
		mController = dragger;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return mController.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mController.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mController.onTouchEvent(event);
	}
	
	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		return mController.dispatchUnhandledMove(focused, direction);
	}
	
	@Override
	public void onDropCompleted(View target, boolean success) {
		
	}
	
}
