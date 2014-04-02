package com.example.sample3spinner;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<String> mItems = new ArrayList<String>();
	
	public MyAdapter(Context context, String[] array) {
		mContext = context;
		Collections.addAll(mItems, array);
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
		TextView view;
		if (convertView == null) {
			view = new TextView(mContext);
		} else {
			view = (TextView)convertView;
		}
		view.setText(mItems.get(position));
		return view;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		CheckedTextView view;
		if (convertView == null) {
			view = new CheckedTextView(mContext);
		} else {
			view = (CheckedTextView)convertView;
		}
		view.setText(mItems.get(position));
		return view;
	}

}
