package com.example.testcustomlistsample;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements MyItemView.OnItemImageClickListener {

	List<MyData> mData;
	Context mContext;
	
	OnAdapterItemClickListener mListener;
	
	public interface OnAdapterItemClickListener {
		public void onAdapterItemClick(MyData data);
	}
	
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
		mListener = listener;
	}
	
	public MyAdapter(Context context, List<MyData> data) {
		mData = data;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public MyData getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		TextView v = new TextView(mContext);
//		v.setText(mData.get(position).name);
//		MyItemView v = new MyItemView(mContext);
		MyItemView v;
		if (convertView == null) {
			v = new MyItemView(mContext);
			v.setOnItemImageClickListener(this);
		} else {
			v = (MyItemView)convertView;
		}
		
		v.setMyData(mData.get(position));
		return v;
	}

	public void add(MyData str) {
		mData.add(str);
		notifyDataSetChanged();
	}

	@Override
	public void onItemImageClick(MyData data) {
		// image click....
		if (mListener != null) {
			mListener.onAdapterItemClick(data);
		}
	}

}
