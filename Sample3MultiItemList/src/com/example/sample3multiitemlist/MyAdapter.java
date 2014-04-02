package com.example.sample3multiitemlist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	List<MyData> items = new ArrayList<MyData>();
	public static final int VIEW_TYPE_COUNT = 3;
	
	public static final int VIEW_TYPE_LEFT_VIEW = 0;
	public static final int VIEW_TYPE_RIGHT_VIEW = 1;
	public static final int VIEW_TYPE_CENTER_VIEW = 2;
	
	public MyAdapter(Context context, List<MyData> items) {
		mContext = context;
		this.items.addAll(items);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public MyData getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}
	
	@Override
	public int getItemViewType(int position) {
		MyData d = items.get(position);
		switch(d.type) {
		case MyData.TYPE_LEFT :
			return VIEW_TYPE_LEFT_VIEW;
		case MyData.TYPE_RIGHT :
			return VIEW_TYPE_RIGHT_VIEW;
		case MyData.TYPE_CENTER :
			return VIEW_TYPE_CENTER_VIEW;
		}
		return VIEW_TYPE_LEFT_VIEW;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch(getItemViewType(position)) {
		case VIEW_TYPE_RIGHT_VIEW :
			RightItemView rightView;
			if (convertView == null) {
				rightView = new RightItemView(mContext);
			} else {
				rightView = (RightItemView)convertView;
			}
			rightView.setMyData(items.get(position));
			return rightView;
		case VIEW_TYPE_CENTER_VIEW :
			CenterItemView centerView;
			if (convertView == null) {
				centerView = new CenterItemView(mContext);
			} else {
				centerView = (CenterItemView)convertView;
			}
			centerView.setMyData(items.get(position));
			return centerView;
		case VIEW_TYPE_LEFT_VIEW :
		default :
			LeftItemView leftView;
			if (convertView == null) {
				leftView = new LeftItemView(mContext);
			} else {
				leftView = (LeftItemView)convertView;
			}
			leftView.setMyData(items.get(position));
			return leftView;
		}
	}

}
