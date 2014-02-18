package com.example.samplegallerytest;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;

public class MyGalleryAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<MyData> list = new ArrayList<MyData>();
	
	public MyGalleryAdapter(Context context,MyData[] array) {
		mContext = context;
		Collections.addAll(list, array);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view = null;
		if (convertView == null) {
			view = new ItemView(mContext);
			Gallery.LayoutParams params = new Gallery.LayoutParams(200, 300);
			view.setLayoutParams(params);
		} else {
			view = (ItemView)convertView;
		}
		view.setMyData(list.get(position));
		return view;
	}

}
