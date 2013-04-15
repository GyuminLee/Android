package com.example.hellonetwork;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BookListAdapter extends BaseAdapter {

	ArrayList<NaverBookItem> items = new ArrayList<NaverBookItem>();
	Context mContext;
	
	public BookListAdapter(Context context) {
		mContext = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void addItem(NaverBookItem item) {
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addItem(ArrayList<NaverBookItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BookItemView v = (BookItemView)convertView;
		if (v == null) {
			v = new BookItemView(mContext);
		}
		v.setData(items.get(position));
		// TODO Auto-generated method stub
		return v;
	}

}
