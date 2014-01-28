package com.example.sample2listselectitem;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;

public class MyAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<String> list = new ArrayList<String>();
	
	public MyAdapter(Context context, String[] array) {
		mContext = context;
		list.addAll(Arrays.asList(array));
	}
	
	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	boolean checkable = false;
	
	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView v = null;
		if (convertView == null) {
			v = new ItemView(mContext);
		} else {
			v = (ItemView)convertView;
		}
		v.setCheckable(checkable);
		v.setText(list.get(position));
		return v;
	}

}
