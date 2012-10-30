package org.tacademy.basic.googleplaces.placelist;

import java.util.ArrayList;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GoogleItemAdapter extends BaseAdapter {

	ArrayList<GooglePlaceItem> items;
	Context mContext;
	
	public GoogleItemAdapter(Context context) {
		mContext = context;
		items = new ArrayList<GooglePlaceItem>();
	}
	
	public void addAll(ArrayList<GooglePlaceItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void clear() {
		items.clear();
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
		GooglePlaceItemView v = (GooglePlaceItemView)convertView;
		if (v == null) {
			v = new GooglePlaceItemView(mContext);
		}
		v.setData((GooglePlaceItem)getItem(position));
		return v;
	}

}
