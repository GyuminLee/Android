package com.example.hellonaveropenapi;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	ArrayList<MovieItem> items = new ArrayList<MovieItem>();
	Context mContext;
	
	public MyAdapter(Context context, ArrayList<MovieItem> items) {
		mContext = context;
		this.items.addAll(items);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public MovieItem getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view;
		if (convertView == null) {
			view = new ItemView(mContext);
		} else {
			view = (ItemView)convertView;
		}
		view.setData(items.get(position));
		return view;
	}

}
