package com.example.hellocustomlisttest2;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<String> items = new ArrayList<String>();
	
	public MyAdapter(Context context, ArrayList<String> items) {
		mContext = context;
		this.items.addAll(items);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int position) {
		
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	int choiceMode;
	public void setChoiceMode(int mode) {
		choiceMode = mode;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view;
		if (convertView == null) {
			view = new ItemView(mContext);
		} else {
			view = (ItemView)convertView;
		}
		view.setChoiceMode(choiceMode);
		view.setText(items.get(position));
		return view;
	}

}
