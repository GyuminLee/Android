package com.example.sample2simplelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	ArrayList<String> mItems;
	Context mContext;
	public MyAdapter(Context context,ArrayList<String> items) {
		mContext = context;
		mItems = items;
	}
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public String getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = new TextView(mContext);
		view.setText(mItems.get(position));
		return view;
	}

}
