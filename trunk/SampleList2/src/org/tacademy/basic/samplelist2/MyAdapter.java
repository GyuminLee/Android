package org.tacademy.basic.samplelist2;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements MyListItem.OnMyItemClickListener {

	Context mContext;
	ArrayList<MyData> mData = new ArrayList<MyData>();
	
	public interface OnMyAdapterListener {
		public void onItemClick(MyData data);
	}
	
	OnMyAdapterListener mListener;
	
	public void setOnMyAdapterListener(OnMyAdapterListener listener) {
		mListener = listener;
	}	
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	public MyAdapter(Context context,MyData[] items) {
		mContext = context;
		for (MyData item : items) {
			mData.add(item);
		}
	}
	
	public void add(MyData item) {
		mData.add(item);
		notifyDataSetChanged();
	}
	
	public void add(ArrayList<MyData> items) {
		mData.addAll(items);
		notifyDataSetChanged();
	}
	
	public void add(MyData[] items) {
		for (MyData item : items) {
			mData.add(item);
		}
		notifyDataSetChanged();
	}
	
	public int getCount() {
		return mData.size();
	}

	public Object getItem(int position) {
		return mData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		MyListItem itemView = (MyListItem)convertView;
		if (itemView == null) {
			itemView = new MyListItem(mContext);
			itemView.setOnMyItemClickListener(this);
		}
		itemView.setData((MyData)getItem(position));
		
		return itemView;
	}

	public void onItemClick(MyData data) {
		if (mListener != null) {
			mListener.onItemClick(data);
		}
	}

}
