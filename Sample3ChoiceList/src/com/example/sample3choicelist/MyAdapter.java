package com.example.sample3choicelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<String> mItems = new ArrayList<String>();
	
	public MyAdapter(Context context,ArrayList<String> items) {
		mContext = context;
		mItems.addAll(items);
	}
	
	@Override
	public int getCount() {
		
		return mItems.size();
	}

	@Override
	public String getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view;
		if (convertView == null) {
			view = new ItemView(mContext);
		} else {
			view = (ItemView)convertView;
		}
		ListView listView = (ListView)parent;
		view.setText(mItems.get(position));
		view.setChoiceMode(listView.getChoiceMode());
		return view;
	}

}
