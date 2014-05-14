package com.example.sampledynamicview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class DynamicLayout extends LinearLayout implements DataAdapter.DataChangeObserver {

	public DynamicLayout(Context context) {
		super(context);
	}

	public DynamicLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DynamicLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	DataAdapter mAdapter;
	public void setDataAdapter(DataAdapter adapter) {
		adapter.setDataChangeObserver(this);
		mAdapter = adapter;
		redraw();
	}
	
	private void redraw() {
		int size = mAdapter.getCount();
		removeAllViews();
		for (int i = 0; i < size; i++) {
			addView(mAdapter.getView(i));
		}
	}
	@Override
	public void onDataChanged() {
		redraw();
	}

	 
}
