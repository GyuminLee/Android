package com.example.sample4multitypelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	public static final int ITEM_TYPE_COUNT = 2;
	public static final int VIEW_TYPE_LEFT = 0;
	public static final int VIEW_TYPE_RIGHT = 1;
	ArrayList<MyData> items = new ArrayList<MyData>();
	Context mContext;
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(MyData data) {
		items.add(data);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return ITEM_TYPE_COUNT;
	}
	
	@Override
	public int getItemViewType(int position) {
		int type = items.get(position).type;
		if ( type == MyData.DATA_TYPE_LEFT) {
			return VIEW_TYPE_LEFT;
		} else if (type == MyData.DATA_TYPE_RIGHT) {
			return VIEW_TYPE_RIGHT;
		} 
		return VIEW_TYPE_LEFT;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (getItemViewType(position)) {
		case VIEW_TYPE_LEFT:
			ItemViewLeft leftView;
			if (convertView == null) {
				leftView = new ItemViewLeft(mContext);
			} else {
				leftView = (ItemViewLeft)convertView;
			}
			leftView.setMyData(items.get(position));
			return leftView;
		case VIEW_TYPE_RIGHT:
			ItemViewRight rightView;
			if (convertView == null) {
				rightView = new ItemViewRight(mContext);
			} else {
				rightView = (ItemViewRight)convertView;
			}
			rightView.setMyData(items.get(position));
			return rightView;
		}
		return null;
	}

}
