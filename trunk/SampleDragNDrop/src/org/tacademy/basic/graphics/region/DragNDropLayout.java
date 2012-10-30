package org.tacademy.basic.graphics.region;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class DragNDropLayout extends ViewGroup implements DragController.DragListener {

	private static final String TAG = "DragNDropLayout";
	
	Context mContext;
	DragController mDragController;
	float mScaleFactor;
	private ScaleGestureDetector scaleDetector;
	private Scroller mScroller;
	private GestureDetector detector;
	private View mScaleView = null;
	
	public static final float SCALE_MIN_VALUE = 0.6f;
	public static final float SCALE_MAX_VALUE = 4.0f;
		
	public boolean setScaleItem(int resId) {
		View view;
		view = findViewById(resId);
		if (view != null && view.getVisibility() != View.GONE) {
			mScaleView = view;
			return true;
		}
		return false;
	}
	
	public boolean isItemScaleMode() {
		return (mScaleView != null);
	}
	
	public boolean setScaleItem(View view) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child == view && view.getVisibility() != View.GONE) {
				mScaleView = view;
				return true;
			}
		}
		return false;
	}
	
	public void resetScaleItem() {
		mScaleView = null;
	}
	
	public interface OnDragAndDropEventListener {
		public void onDragStart(View v, Object info);
		public void onDragEnd(View v, Object info);
	}
	
	OnDragAndDropEventListener mListener;
	
	public void setOnDragAndDropEventListener(OnDragAndDropEventListener listener) {
		mListener = listener;
	}
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			if (mScaleView == null) {
				mScaleFactor *= detector.getScaleFactor();
				mScaleFactor = adjustScale(mScaleFactor);
				invalidate();
				requestLayout();
			} else {
				LayoutParams param = (LayoutParams)mScaleView.getLayoutParams();
				param.fScale *= detector.getScaleFactor();
				updateViewLayout(mScaleView, param);
			}
			return true;
		}
		
	}
	
	private class FlingListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onFling....");
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onScroll....");
			if (mScaleView == null) {
				scrollBy((int)distanceX,(int)distanceY);
			} else {
//				LayoutParams param = (LayoutParams)mScaleView.getLayoutParams();
//				param.x -= (int)(distanceX / mScaleFactor);
//				param.y -= (int)(distanceY / mScaleFactor);
//				updateViewLayout(mScaleView, param);
			}
			return true;
		}
		
	}
	
	private float adjustScale(float scale) {
		return Math.max(SCALE_MIN_VALUE, Math.min(scale, SCALE_MAX_VALUE));
	}
	
	public DragNDropLayout(Context context) {
		this(context,null,0);
	}

	public DragNDropLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public DragNDropLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		mDragController = new DragController(context);
		mDragController.setDragListener(this);
		mScaleFactor = 1.0f;
		scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		detector = new GestureDetector(context,new FlingListener());
	}

	public void setScale(float zoom) {
		mScaleFactor *= zoom;
		mScaleFactor = adjustScale(mScaleFactor);
		invalidate();
		requestLayout();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int count = getChildCount();
		
		int maxWidth = 0;
		int maxHeight = 0;
		
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		// scale에 따른 사이즈를 다시 계산함.
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				LayoutParams lp = (LayoutParams)child.getLayoutParams();
				int widthPadding = getPaddingLeft() + getPaddingRight();
				int heightPadding = getPaddingTop() + getPaddingBottom();
				int childWidth = child.getMeasuredWidth();
				int childHeight = child.getMeasuredHeight();
				if (lp.isScalable) {
					childWidth = (int)(childWidth * lp.fScale * mScaleFactor);
					childHeight = (int)(childHeight *lp.fScale * mScaleFactor);
				}
				int childWidthMeasureSpec = this.getChildMeasureSpec(widthMeasureSpec, widthPadding, childWidth);
				int childHeightMeasureSpec = this.getChildMeasureSpec(heightMeasureSpec, heightPadding, childHeight);
				child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
			}
		}
		
		for (int i = 0; i < count ; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				int right,bottom;
				LayoutParams lp = (LayoutParams)child.getLayoutParams();
				right = (int)(lp.x * mScaleFactor) + child.getMeasuredWidth();
				bottom = (int)(lp.y * mScaleFactor) + child.getMeasuredHeight();
				
				maxWidth = Math.max(maxWidth, right);
				maxHeight = Math.max(maxHeight, bottom);
			}
		}
		
		maxWidth += getPaddingLeft() + getPaddingRight();
		maxHeight += getPaddingTop() + getPaddingBottom();
		
		maxWidth = Math.max(maxWidth, this.getSuggestedMinimumWidth());
		maxHeight = Math.max(maxHeight, this.getSuggestedMinimumHeight());
		
		setMeasuredDimension(resolveSize(maxWidth,widthMeasureSpec), 
				resolveSize(maxHeight, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		//super.onLayout(changed, left, top, right, bottom);
		int count = getChildCount();
		
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				LayoutParams lp = (LayoutParams)child.getLayoutParams();
				
				int childLeft = paddingLeft + (int)(lp.x * mScaleFactor);
				int childTop = paddingTop + (int)(lp.y * mScaleFactor);
				int width = child.getMeasuredWidth();
				int height = child.getMeasuredHeight();
				child.layout(childLeft, childTop, 
						childLeft + width, childTop + height);
			}
			
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
	
	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return (p instanceof LayoutParams);
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,0,0);
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new LayoutParams(getContext(),attrs);
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
		return new LayoutParams(p);
	}

	public static class LayoutParams extends ViewGroup.LayoutParams {

		public int x = 0;
		public int y = 0;
		public boolean isScalable = true;
		public float fScale = 1.0f;
		
		public LayoutParams(int width, int height, int x, int y) {
			this(width,height,x,y,true,1.0f);
		}
		
		public LayoutParams(int width, int height, int x, int y, boolean isScalable) {
			this(width,height,x,y,isScalable,1.0f);
		}
		
		public LayoutParams(int width, int height, int x, int y, boolean isScale, float scale) {
			super(width, height);
			this.x = x;
			this.y = y;
			this.isScalable = isScalable;
			this.fScale = scale;
		}

		public LayoutParams(ViewGroup.LayoutParams params) {
			super(params);
		}

		public LayoutParams(Context context, AttributeSet attr) {
			super(context, attr);
			TypedArray ta = context.obtainStyledAttributes(attr,R.styleable.DragNDrapLayout );
			this.x = (int)ta.getDimension(R.styleable.DragNDrapLayout_android_layout_x, 0.0f);
			this.y = (int)ta.getDimension(R.styleable.DragNDrapLayout_android_layout_y, 0.0f);
			this.isScalable = ta.getBoolean(R.styleable.DragNDrapLayout_layout_scalable, true);
			this.fScale = ta.getFloat(R.styleable.DragNDrapLayout_layout_scale, 1.0f);
		}
		
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mDragController.onInterceptTouchEvent(ev)) {
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (mDragController.onTouchEvent(event)) {
			return true;
		}
		
		scaleDetector.onTouchEvent(event);
		if (detector.onTouchEvent(event)) {
			return true;
		}
		super.onTouchEvent(event);
		return true;
	}

	public void startDrag(View view, Object info) {
		int count = this.getChildCount();
		for (int i = 0; i < count; i++) {
			View v = getChildAt(i);
			if (v == view) {
				mDragController.startDrag(view, info);
			}
		}
	}

	public void cancelDrag() {
		mDragController.cancelDrag();
	}
	
	public void onDragStart(View source, Object info) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onDragStart(source, info);
		}
	}

	public void onDrop(View source, int x, int y, int xOffset, int yOffset,
			DragView dragView, Object dragInfo) {
		// TODO Auto-generated method stub
        int[] loc = new int[2];
        int[] wloc = new int[2];
        this.getLocationOnScreen(loc);
        LayoutParams params = (LayoutParams)source.getLayoutParams();
        int left = (int)((float)(x  - xOffset - loc[0]) / mScaleFactor ) + (int)((float)getScrollX() / mScaleFactor);
	    int top = (int) ((float)(y - yOffset  - loc[1]) / mScaleFactor) + (int)((float)getScrollY() / mScaleFactor);
	    LayoutParams lp = new LayoutParams (params.width, params.height, left, top, params.isScalable, params.fScale);
	    updateViewLayout(source, lp);
		
	}

	public void onDragEnd() {
		// TODO Auto-generated method stub
		
	}

	public void onDropCompleted(View source, Object info) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onDragEnd(source, info);
		}
	}

}
