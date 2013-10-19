package com.example.samplecustomlisttest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyItemAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<ItemData> dataList = new ArrayList<ItemData>();
	
	public MyItemAdapter(Context context) {
		mContext = context;
	}
	
	public MyItemAdapter(Context context, ArrayList<ItemData> data) {
		mContext = context;
		dataList.addAll(data);
	}
	
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public ItemData getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView v = new ItemView(mContext);
		v.setData(getItem(position));
		return v;
	}

}
