package org.tacademy.network.rss.navermovie;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class NaverMovieAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<NaverMovieItem> items;
	private final static String TAG = "NaverMovieAdapter";
	
	public NaverMovieAdapter(Context context) {
		this(context,new ArrayList<NaverMovieItem>());
	}
	
	public NaverMovieAdapter(Context context,ArrayList<NaverMovieItem> items) {
		mContext = context;
		this.items = items;
	}
	
	public void addItem(NaverMovieItem item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void addItem(ArrayList<NaverMovieItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void removeItem(NaverMovieItem item) {
		items.remove(item);
		notifyDataSetChanged();
	}
	
	public void removeItems(ArrayList<NaverMovieItem> items) {
		for (int i = 0; i < items.size(); i++) {
			removeItem(items.get(i));
		}
		notifyDataSetChanged();
	}
	
	public void removeAll() {
		items.clear();
	}
	
	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup viewGroup) {
		Log.i(TAG,"getView : position - " + position);
		MovieItemView v = (MovieItemView)convertView;
		if (v == null) {
			v = new MovieItemView(mContext);
		}
		
		v.setData((NaverMovieItem)getItem(position));
		return v;
	}
}
