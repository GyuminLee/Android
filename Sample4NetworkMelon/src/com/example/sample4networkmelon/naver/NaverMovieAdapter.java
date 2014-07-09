package com.example.sample4networkmelon.naver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sample4networkmelon.entity.MovieItem;

public class NaverMovieAdapter extends BaseAdapter {

	ArrayList<MovieItem> items = new ArrayList<MovieItem>();
	Context mContext;
	public NaverMovieAdapter(Context context) {
		mContext = context;
	}
	
	public void add(MovieItem item) {
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(List<MovieItem> items) {
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
		ItemView v;
		if (convertView == null) {
			v = new ItemView(mContext);
		} else {
			v = (ItemView)convertView;
		}
		v.setMovieItem(items.get(position));
		return v;
	}

}
