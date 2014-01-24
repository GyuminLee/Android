package com.example.samplegesturetest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

public class FlingGridView extends GridView {

	public static final int BOTTOM_TO_UP = 1;
	public static final int UP_TO_BOTTOM = 2;
	public static final int LEFT_TO_RIGHT = 3;
	public static final int RIGHT_TO_LEFT = 4;

	public interface OnFlingGestureListener {
		public boolean onFlingGesture(FlingGridView v, int orientation);
	}

	OnFlingGestureListener mListener = null;

	public void setOnFlingGestureListener(OnFlingGestureListener listener) {
		mListener = listener;
	}

	public FlingGridView(Context context) {
		super(context);
		init();
	}

	public FlingGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public FlingGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	GestureDetector mDetector;

	public interface OnSwipeListener {
		public void onSwipe(View v);
	}

	private void init() {
		mDetector = new GestureDetector(getContext(),
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
							if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH
									|| Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
								return false;
							}
							if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
								if (mListener != null) {
									return mListener.onFlingGesture(
											FlingGridView.this, BOTTOM_TO_UP);
								}
							} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
								if (mListener != null) {
									return mListener.onFlingGesture(
											FlingGridView.this, UP_TO_BOTTOM);
								}
							}
						} else {
							if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
								return false;
							}
							if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
								if (mListener != null) {
									return mListener.onFlingGesture(
											FlingGridView.this, RIGHT_TO_LEFT);
								}
							} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
								if (mListener != null) {
									return mListener.onFlingGesture(
											FlingGridView.this, LEFT_TO_RIGHT);
								}
							}
						}
						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mDetector.onTouchEvent(ev);
		
		return super.onTouchEvent(ev);
	}
}
