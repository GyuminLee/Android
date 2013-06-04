package com.example.testcustomlistsample;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	List<MyData> mData;
	Context mContext;
	
	public MyAdapter(Context context, List<MyData> data) {
		mData = data;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public MyData getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		TextView v = new TextView(mContext);
//		v.setText(mData.get(position).name);
		MyItemView v = new MyItemView(mContext);
		v.setMyData(mData.get(position));
		return v;
	}

	public void add(MyData str) {
		mData.add(str);
		notifyDataSetChanged();
	}

}
