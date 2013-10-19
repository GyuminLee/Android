package com.example.samplecustomlisttest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyItemAdapter extends BaseAdapter implements ItemView.OnItemImageClickListener {

	Context mContext;
	ArrayList<ItemData> dataList = new ArrayList<ItemData>();
	
	public interface OnAdapterImageClickListener {
		public void onAdapterImageClicked(MyItemAdapter adapter, ItemView view, ItemData item);
	}
	
	OnAdapterImageClickListener mListener;
	
	public void setOnAdapterImageClickListener(OnAdapterImageClickListener listener) {
		mListener = listener;
	}
	
	public MyItemAdapter(Context context) {
		mContext = context;
	}
	
	public MyItemAdapter(Context context, ArrayList<ItemData> data) {
		mContext = context;
		dataList.addAll(data);
	}
	
	public void add(ItemData item) {
		dataList.add(item);
		notifyDataSetChanged();
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
		
		ItemView v;
		if (convertView == null) {
			v = new ItemView(mContext);
			v.setOnItemImageClickListener(this);
		} else {
			v = (ItemView)convertView;
		}
		v.setData(getItem(position));
		return v;
	}

	@Override
	public void onItemImageClicked(ItemView v, ItemData item) {
		if (mListener != null) {
			mListener.onAdapterImageClicked(this, v, item);
		}
	}

}
