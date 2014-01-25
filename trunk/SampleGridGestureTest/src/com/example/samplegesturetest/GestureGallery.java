package com.example.samplegesturetest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

public class GestureGallery extends Gallery {

	public GestureGallery(Context context) {
		super(context);
	}

	public GestureGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GestureGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private View getChildView(MotionEvent e) {
		int startMotionPosition = pointToPosition((int) e.getX(),
				(int) e.getY());
		return getChildAt(startMotionPosition - getFirstVisiblePosition());
	}

	private GestureItemViewGroup castGestureItemViewGroup(View v) {
		if (v == null)
			return null;
		if (v instanceof GestureItemViewGroup) {
			return (GestureItemViewGroup) v;
		}
		return null;
	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		GestureItemViewGroup v = castGestureItemViewGroup(getChildView(e));
		if (v != null) {
			if (v.onSingleTapUp(e)) {
				return true;
			}
		}
		return super.onSingleTapUp(e);
	}
	
}
