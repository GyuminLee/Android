package com.example.sample3customlist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements ItemView.OnImageClickListener {

	Context mContext;
	ArrayList<MyData> mItems = new ArrayList<MyData>();
	
	public interface OnAdapterItemClickListener {
		public void onAdapterItemClick(View v, MyData data);
	}
	
	OnAdapterItemClickListener mListener;
	
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
		mListener = listener;
	}
	
	public MyAdapter(Context context) {
		this(context,null);
	}
	
	public MyAdapter(Context context, List<MyData> items) {
		mContext = context;
		if (items != null) {
			mItems.addAll(items);
		}
	}
	
	public void add(MyData data) {
		mItems.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(List<MyData> items) {
		mItems.addAll(items);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public MyData getItem(int position) {
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
			view.setOnImageClickListener(this);
		} else {
			view = (ItemView)convertView;
		}
		view.setMyData(mItems.get(position));
		return view;
	}

	@Override
	public void onImageClick(View v, MyData data) {
		// ...
		if (mListener != null) {
			mListener.onAdapterItemClick(v, data);
		}
	}

}
