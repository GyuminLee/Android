package com.example.sample2gallerytest;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;

public class MyAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<MyData> list = new ArrayList<MyData>();
	
	public MyAdapter(Context context, MyData[] array) {
		mContext = context;
		Collections.addAll(list, array);
	}
	
	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view = null;
		if (convertView == null) {
			view = new ItemView(mContext);
			Gallery.LayoutParams params = new Gallery.LayoutParams(300,400);
			view.setLayoutParams(params);
		} else {
			view = (ItemView)convertView;
		}
		view.setMyData(list.get(position));
		return view;
	}

}
