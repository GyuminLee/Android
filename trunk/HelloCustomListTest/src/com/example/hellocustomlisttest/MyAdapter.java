package com.example.hellocustomlisttest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<MyData> items = new ArrayList<MyData>();

	public MyAdapter(Context context, ArrayList<MyData> list) {
		mContext = context;
		items.addAll(list);
	}

	public void add(MyData data) {
		items.add(data);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return items.size();
	}

	@Override
	public MyData getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyItemView view;
		if (convertView == null) {
			view = new MyItemView(mContext);
		} else {
			view = (MyItemView) convertView;
		}
		view.setMyData(items.get(position));
		return view;
	}

}
