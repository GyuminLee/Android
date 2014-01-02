package com.example.sample2simplelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sample2simplelist.model.MyData;
import com.example.sample2simplelist.view.ItemView;

public class MyAdapter extends BaseAdapter {

	ArrayList<MyData> mItems;
	Context mContext;
	public MyAdapter(Context context,ArrayList<MyData> items) {
		mContext = context;
		mItems = items;
	}
	
	public void add(MyData item) {
		mItems.add(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public MyData getItem(int position) {
		return mItems.get(position);
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
		} else {
			view = (ItemView)convertView;
		}
		
		view.setMyData(mItems.get(position));
		
		return view;
	}

}
