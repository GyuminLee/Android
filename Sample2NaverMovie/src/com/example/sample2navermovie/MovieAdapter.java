package com.example.sample2navermovie;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MovieAdapter extends BaseAdapter {
	ArrayList<MovieItem> items = new ArrayList<MovieItem>();
	Context mContext;

	public MovieAdapter(Context context, ArrayList<MovieItem> items) {
		mContext = context;
		if (items != null) {
			this.items.addAll(items);
		}
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
			v = (ItemView) convertView;
		}
		v.setMovieItem(items.get(position));
		return v;
	}
}
