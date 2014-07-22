package com.example.samplegesturetest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

public class GestureGridView extends GridView {

	public static final int SWIPE_BOTTOM_TO_UP = 1;
	public static final int SWIPE_UP_TO_BOTTOM = 2;
	public static final int SWIPE_LEFT_TO_RIGHT = 3;
	public static final int SWIPE_RIGHT_TO_LEFT = 4;

	OnSwipeListener mListener = null;
	OnItemSwipeListener mSwipeListener = null;
	
	public void setOnItemSwipeListener(OnItemSwipeListener listener) {
		mSwipeListener = listener;
	}
	

	public void setOnSwipeListener(OnSwipeListener listener) {
		mListener = listener;
	}

	public GestureGridView(Context context) {
		super(context);
		init();
	}

	public GestureGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GestureGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	GestureDetector mDetector;

	private View getMatchChildView(MotionEvent e1, MotionEvent e2) {
		int startMotionPosition = pointToPosition((int) e1.getX(),
				(int) e1.getY());
		int endMotionPosition = pointToPosition((int) e2.getX(),
				(int) e2.getY());
		if (startMotionPosition == endMotionPosition) {
			View v = getChildAt(endMotionPosition - getFirstVisiblePosition());
			return v;
		}
		return null;
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

	private int getMatchPosition(MotionEvent e1, MotionEvent e2) {
		int startMotionPosition = pointToPosition((int) e1.getX(),
				(int) e1.getY());
		int endMotionPosition = pointToPosition((int) e2.getX(),
				(int) e2.getY());
		if (startMotionPosition == endMotionPosition) {
			return startMotionPosition;
		}
		return -1;
	}
	
	private boolean performSwipe(MotionEvent e1, MotionEvent e2, int orientation) {
		boolean bConsumed = false;
		GestureItemViewGroup v = castGestureItemViewGroup(getMatchChildView(
				e1, e2));
		if (v != null) {
			bConsumed =  v.onSwipe(v, orientation);
		}
		if (!bConsumed && mSwipeListener != null) {
			int position = getMatchPosition(e1, e2);
			if (position >= 0) {
				View child = getChildAt(position - getFirstVisiblePosition()); 
				bConsumed = mSwipeListener.onItemSwipe(this, child, position, orientation);
			}
		}
		if (!bConsumed && mListener != null) {
			bConsumed = mListener.onSwipe(this, orientation);
		}
		return bConsumed;
	}

	private void init() {
		mDetector = new GestureDetector(getContext(),
				new GestureDetector.SimpleOnGestureListener() {

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						boolean bConsumed = false;
						if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
							if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH
									|| Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
								bConsumed = false;
							} else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
								bConsumed = performSwipe(e1, e2,
										SWIPE_BOTTOM_TO_UP);
							} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
								bConsumed = performSwipe(e1, e2,
										SWIPE_UP_TO_BOTTOM);
							}
						} else {
							if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
								bConsumed = false;
							} else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
								bConsumed = performSwipe(e1, e2,
										SWIPE_RIGHT_TO_LEFT);

							} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
								bConsumed = performSwipe(e1, e2,
										SWIPE_LEFT_TO_RIGHT);
							}
						}
						if (!bConsumed) {
							GestureItemViewGroup v = castGestureItemViewGroup(getMatchChildView(
									e1, e2));
							if (v != null) {
								bConsumed = v.onFling(e1, e2, velocityX,
										velocityY);
							}
						}
						return bConsumed;
					}

					@Override
					public boolean onDoubleTap(MotionEvent e) {
						GestureItemViewGroup v = castGestureItemViewGroup(getChildView(e));
						if (v != null) {
							return v.onDoubleTap(e);
						}
						return super.onDoubleTap(e);
					}

					@Override
					public boolean onDoubleTapEvent(MotionEvent e) {
						GestureItemViewGroup v = castGestureItemViewGroup(getChildView(e));
						if (v != null) {
							return v.onDoubleTapEvent(e);
						}
						return super.onDoubleTapEvent(e);
					}

					@Override
					public boolean onDown(MotionEvent e) {
						GestureItemViewGroup v = castGestureItemViewGroup(getChildView(e));
						if (v != null) {
							return v.onDown(e);
						}
						return super.onDown(e);
					}

					@Override
					public void onLongPress(MotionEvent e) {
						GestureItemViewGroup v = castGestureItemViewGroup(getChildView(e));
						if (v != null) {
							v.onLongPress(e);
						}
						super.onLongPress(e);
					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						GestureItemViewGroup v = castGestureItemViewGroup(getMatchChildView(
								e1, e2));
						if (v != null) {
							return v.onScroll(e1, e2, distanceX, distanceY);
						}
						return super.onScroll(e1, e2, distanceX, distanceY);
					}

					@Override
					public void onShowPress(MotionEvent e) {
						GestureItemViewGroup v = castGestureItemViewGroup(getChildView(e));
						if (v != null) {
							v.onShowPress(e);
						}
						super.onShowPress(e);
					}

					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						GestureItemViewGroup v = castGestureItemViewGroup(getChildView(e));
						if (v != null) {
							return v.onSingleTapConfirmed(e);
						}
						return super.onSingleTapConfirmed(e);
					}

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						GestureItemViewGroup v = castGestureItemViewGroup(getChildView(e));
						if (v != null) {
							return v.onSingleTapUp(e);
						}
						return super.onSingleTapUp(e);
					}
				});
	}

	int motionItem;

	boolean downCheckChild = false;

	View oldView;
	boolean oldFocusable;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean bConsumed = false;
		bConsumed = mDetector.onTouchEvent(ev);
		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			GestureItemViewGroup v = castGestureItemViewGroup(getChildView(ev));
			if (v != null) {
				downCheckChild = v.checkChildViewClick(ev);
				if (downCheckChild) {
					oldFocusable = v.hasFocusable();
					v.setFocusable(true);
					oldView = v;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		default:
			break;
		}
		if (!bConsumed) {
			bConsumed = super.onTouchEvent(ev);
		} else {
			int oldAction = ev.getAction();
			ev.setAction(MotionEvent.ACTION_CANCEL);
			bConsumed = super.onTouchEvent(ev);
			ev.setAction(oldAction);
		}
		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			break;
		default:
			if (downCheckChild) {
				downCheckChild = false;
				oldView.setFocusable(oldFocusable);
			}
			break;
		}
		return bConsumed;
	}
}
