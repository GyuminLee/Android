package com.example.testnetworksample2;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	ArrayList<NaverMovieItem> items;
	Context mContext;
	public MyAdapter(Context context, ArrayList<NaverMovieItem> items) {
		mContext = context;
		if (items == null) {
			items = new ArrayList<NaverMovieItem>();
		}
		this.items = items;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public NaverMovieItem getItem(int position) {
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
		ItemView v;
		if (convertView == null) {
			v = new ItemView(mContext);
		} else {
			v = (ItemView)convertView;
		}
		v.setItemData(items.get(position));
		return v;
	}

}
