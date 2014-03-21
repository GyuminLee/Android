package com.example.testnavermovies;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<MovieItem> items = new ArrayList<MovieItem>();
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void addAll(ArrayList<MovieItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public MovieItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view = (ItemView)convertView;
		if (view == null) {
			view = new ItemView(mContext);
		}
		view.setMovieItem(items.get(position));
		return view;
	}

}
