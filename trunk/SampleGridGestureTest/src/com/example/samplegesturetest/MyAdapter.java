package com.example.samplegesturetest;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<String> mList = new ArrayList<String>();
	
	public MyAdapter(Context context, String[] data) {
		mContext = context;
		mList.addAll(Arrays.asList(data));
	}
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView v = (ItemView)convertView;
		if (v == null) {
			v = new ItemView(mContext);
		}
		v.setText(mList.get(position));
		return v;
	}

}
