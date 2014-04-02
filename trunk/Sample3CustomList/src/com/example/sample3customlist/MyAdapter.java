package com.example.sample3customlist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<MyData> mItems = new ArrayList<MyData>();
	
	public MyAdapter(Context context) {
		this(context,null);
	}
	
	public MyAdapter(Context context, List<MyData> items) {
		mContext = context;
		if (items != null) {
			mItems.addAll(items);
		}
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public MyData getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view = new ItemView(mContext);
		view.setMyData(mItems.get(position));
		return view;
	}

}
