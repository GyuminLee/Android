package com.begentgroup.draganddrop.view;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.begentgroup.draganddrop.DragController;
import com.begentgroup.draganddrop.DragSource;

public class IconGridView extends LinearLayout implements IconDropTargetView.OnDropListener, OnLongClickListener {

	DragController mController;
	DragSource mSource;
	ArrayList<IconDropTargetView> dropViewList = new ArrayList<IconDropTargetView>();
	ListAdapter mAdapter;
	int mIndex = 0;
	DataSetObserver mObserver;
	public interface OnItemClickListener {
		public void onItemClick(ViewGroup parent, View view, int position, long id);
	}
	
	OnItemClickListener mItemClickListener;
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		mItemClickListener = listener;
	}

	public interface OnDragAndDropListener {
		public void onStartDrag(ViewGroup parent, View childView, int position, Object item);
		public void onDropComplete(ViewGroup parent, View childView, int position, Object item);
	}
	
	OnDragAndDropListener mDragAndDropListener;
	
	public void setOnDragAndDropListener(OnDragAndDropListener listener) {
		mDragAndDropListener = listener;
	}
	
	public interface OnNotifyNextListener {
		public void onNotifyNext(View v, boolean isNext);
	}
	
	OnNotifyNextListener mNotifyListener;
	public void setOnNotifyNextListener(OnNotifyNextListener listener) {
		mNotifyListener = listener;
	}
	
	class IconGridDataSetObserver extends DataSetObserver {
		@Override
		public void onChanged() {
			super.onChanged();
			requestLayout();
		}
		
		@Override
		public void onInvalidated() {
			super.onInvalidated();
			requestLayout();
		}
	}
	
	public IconGridView(Context context) {
		super(context);
		init();
	}

	public IconGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public IconGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setAdapter(ListAdapter adapter) {
		mAdapter = adapter;
		if (mObserver == null) {
			mObserver = new IconGridDataSetObserver();
		}
		mAdapter.registerDataSetObserver(mObserver);
	}
	
	public void setDragController(DragController controller) {
		mController = controller;
		controller.addDropTarget(prev);
		controller.addDropTarget(next);
		for (IconDropTargetView v : dropViewList) {
			controller.addDropTarget(v);
		}
	}
	
	public DragController getDragController() {
		return mController;
	}
	
	public void setDragSource(DragSource source) {
		mSource = source;
	}
	
	public DragSource getDragSource() {
		return mSource;
	}
	
	PrevNextImageView prev,next;
	int columnSize = 3;
	int rowSize = 2;

	public boolean showNext() {
		int size = 0;
		if (mAdapter != null) {
			size = mAdapter.getCount();
		}
		int pageCount = columnSize * rowSize;
		if (mIndex * pageCount < size) {
			mIndex++;
			notifyNext(true);
			requestLayout();
			return true;
		}
		return false;
	}
	
	public boolean showPrevious() {
		if (mIndex > 0) {
			mIndex--;
			notifyNext(false);
			requestLayout();
			return true;
		}
		return false;
	}

	public void notifyNext(boolean isNext) {
		if (mNotifyListener != null) {
			mNotifyListener.onNotifyNext(this, isNext);
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		if (mAdapter != null) {
			int pageSize = columnSize * rowSize;
			int startPosition = mIndex * pageSize;
			int count = mAdapter.getCount();
			for (int i = 0, index = startPosition; i < pageSize; i++, index++) {
				IconDropTargetView targetView = dropViewList.get(i);
				View view = null;
				if (targetView.getChildCount() > 0) {
					view = targetView.getChildAt(0);
				}
				View newView = null;
				if (index < count) {
					targetView.setVisibility(View.VISIBLE);
					newView = mAdapter.getView(index, view, this);
					if (newView.getLayoutParams() == null) {
						newView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
					}
					if (view != newView) {
						targetView.removeView(view);
						FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
						targetView.addView(newView, params);
					}
				} else {
					targetView.setVisibility(View.INVISIBLE);
				}
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);		
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
	
	private LinearLayout.LayoutParams makeLayoutParams() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		return params;
	}
	
	private void init() {
		setOrientation(LinearLayout.HORIZONTAL);

		prev = new PrevNextImageView(getContext());
		prev.setLayoutParams(makeLayoutParams());
		prev.setImageResource(android.R.drawable.ic_media_previous);
		prev.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPrevious();				
			}
		});
		prev.setOnIconClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPrevious();
			}
		});
		addView(prev);
		LinearLayout ll = new LinearLayout(getContext());
		ll.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
		addView(ll,params);
		int number = 0;
		for (int i = 0; i < rowSize; i++) {
			LinearLayout cl = new LinearLayout(getContext());
			cl.setOrientation(LinearLayout.HORIZONTAL);
			params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
			ll.addView(cl,params);
			for (int j = 0; j < columnSize; j++) {
				IconDropTargetView v = new IconDropTargetView(getContext());
				params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
				cl.addView(v,params);
				v.setNumber(number++);
				v.setOnDropListener(this);
				v.setOnLongClickListener(this);
				v.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (mItemClickListener != null && mAdapter != null) {
							IconDropTargetView view = (IconDropTargetView)v;
							if (view.getChildCount() > 0) {
								View childView = view.getChildAt(0);
								int position = mIndex * columnSize * rowSize + view.getNumber();
								long id = mAdapter.getItemId(position);
								mItemClickListener.onItemClick(IconGridView.this, childView, position, id);
							}
						}
					}
				});
				dropViewList.add(v);
			}
		}
		next = new PrevNextImageView(getContext());
		next.setLayoutParams(makeLayoutParams());
		next.setImageResource(android.R.drawable.ic_media_next);
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showNext();
			}
		});
		next.setOnIconClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showNext();
			}
		});
		addView(next);
	}

	@Override
	public void onDrop(View view, int number, Object dragInfo) {
		if (mDragAndDropListener != null) {
			int position = mIndex * columnSize * rowSize + number;
			mDragAndDropListener.onDropComplete(this, view, position, dragInfo);
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if (mAdapter == null) return false;
		IconDropTargetView targetView = (IconDropTargetView)v;
		View childView = null;
		if (targetView.getChildCount() > 0) {
			childView = targetView.getChildAt(0);
		}
		int position = mIndex * columnSize * rowSize + targetView.getNumber();
		if (position < mAdapter.getCount()) {
			Object dragInfo = mAdapter.getItem(position);
			mController.startDrag(childView, mSource, dragInfo, DragController.DRAG_ACTION_COPY);
			if (mDragAndDropListener != null) {
				mDragAndDropListener.onStartDrag(this, childView, position, dragInfo);
			}
		}
		return false;
	}
	
}
