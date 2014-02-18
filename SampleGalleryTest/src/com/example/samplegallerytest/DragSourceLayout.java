package com.example.samplegallerytest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class DragSourceLayout extends LinearLayout implements DragSource {

	public DragSourceLayout(Context context) {
		super(context);
	}

	public DragSourceLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DragSourceLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean allowDrag() {
		return true;
	}

	DragController mDragController;
	
	@Override
	public void setDragController(DragController dragger) {
		mDragController = dragger;
	}

	@Override
	public void onDropCompleted(View target, boolean success) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mDragController.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	boolean isConsumed = mDragController.onInterceptTouchEvent(ev);
    	if (!isConsumed) {
    		isConsumed = super.onInterceptTouchEvent(ev);
    	}
        return isConsumed;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	boolean isConsumed = mDragController.onTouchEvent(ev);
    	if (!isConsumed) {
    		isConsumed = super.onTouchEvent(ev);
    	}
        return isConsumed;
    }

    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
    	boolean isConsumed = mDragController.dispatchUnhandledMove(focused, direction);
    	if (!isConsumed) {
    		isConsumed = super.dispatchUnhandledMove(focused, direction);
    	}
        return isConsumed;
    }

    
}
