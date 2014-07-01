package com.example.sample4choicelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MyAdapter extends BaseAdapter {
	ArrayList<MyData> items = new ArrayList<MyData>();
	Context mContext;
	
	public MyAdapter(Context context) {
		mContext = context;
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
	public Object getItem(int position) {
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
		v.setMyData(items.get(position));
		if (parent instanceof ListView) {
			v.setChoiceMode(((ListView)parent).getChoiceMode());
		}
		return v;
	}

}
