package com.example.sample4customlist;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements ItemView.OnItemDataClickListener {

	ArrayList<MyData> items = new ArrayList<MyData>();
	Context mContext;
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(MyData data) {
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<MyData> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void remove(MyData data) {
		items.remove(data);
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
		ItemView v;
		
		if (convertView == null) {
			v = new ItemView(mContext);
			v.setOnItemDataClickListener(this);
		} else {
			v = (ItemView)convertView;
		}
		
		v.setMyData(items.get(position));
		return v;
	}

	public interface OnAdapterItemClickListener {
		public void onItemLikeClick(View v , MyData data);
	}
	
	OnAdapterItemClickListener mAdapterListener;
	
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
		mAdapterListener = listener;
	}
	
	@Override
	public void onLikeClick(View v, MyData data) {
		if (mAdapterListener != null) {
			mAdapterListener.onItemLikeClick(v, data);
		}
	}

}
