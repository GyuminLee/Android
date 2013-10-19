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
		
		switch(getItemViewType(position)) {
		case ITEM_TYPE_ONE :
			ItemView v;
			if (convertView == null) {
				v = new ItemView(mContext);
				v.setOnItemImageClickListener(this);
			} else {
				v = (ItemView)convertView;
			}
			v.setData(getItem(position));
			return v;
		case ITEM_TYPE_TWO :
			ItemViewRight vr;
			if (convertView == null) {
				vr = new ItemViewRight(mContext);
			} else {
				vr = (ItemViewRight)convertView;
			}
			vr.setData(getItem(position));
			return vr;
		}
		return null;
	}

	@Override
	public void onItemImageClicked(ItemView v, ItemData item) {
		if (mListener != null) {
			mListener.onAdapterImageClicked(this, v, item);
		}
	}

	public static final int ITEM_VIEW_TYPE_COUNT = 2;
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return ITEM_VIEW_TYPE_COUNT;
	}

	public static final int ITEM_TYPE_ONE = 0;
	public static final int ITEM_TYPE_TWO = 1;
	
	@Override
	public int getItemViewType(int position) {
		if (position % 2 == 0) {
			return ITEM_TYPE_ONE;
		} else {
			return ITEM_TYPE_TWO;
		}
	}
}
