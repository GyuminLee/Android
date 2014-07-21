package com.example.sample4staggeredgridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	String[] urls;
	
	public MyAdapter(Context context, String[] urls) {
		mContext = context;
		this.urls = urls;
	}
	
	@Override
	public int getCount() {
		return urls.length;
	}

	@Override
	public String getItem(int position) {
		return urls[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView v = null;
		if (convertView == null) {
			v = new ItemView(mContext);
		} else {
			v = (ItemView)convertView;
		}
		v.setUrl(urls[position]);
		return v;
	}

}
