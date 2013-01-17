package com.example.samplecustomloader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	List<ItemData> items = new ArrayList<ItemData>();
	Context mContext;
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void set(List<ItemData> items) {
		this.items.clear();
		if (items != null) {
			this.items.addAll(items);
		}
		notifyDataSetChanged();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemView v;
		if (convertView == null) {
			v = new ItemView(mContext);
		} else {
			v = (ItemView)convertView;
		}
		v.setData(items.get(position));
		return v;
	}

}
