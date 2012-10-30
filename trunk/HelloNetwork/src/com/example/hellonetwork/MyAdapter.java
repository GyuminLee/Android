package com.example.hellonetwork;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements ItemView.OnImageClickListener {

	Context mContext;
	ArrayList<NaverMovieItem> mDataArray;
	
	public interface OnAdapterImageClickListener {
		public void onImageClick(NaverMovieItem data);
	}
	
	private OnAdapterImageClickListener mListener;
	
	public void setOnAdapterImageClickListener(OnAdapterImageClickListener listener) {
		mListener = listener;
	}
	
	public MyAdapter(Context context) {
		mContext = context;
		mDataArray = new ArrayList<NaverMovieItem>();
	}
	
	public void addItem(NaverMovieItem data) {
		mDataArray.add(data);
		notifyDataSetChanged();
	}
	
	public void addItems(ArrayList<NaverMovieItem> dataArray) {
		mDataArray.addAll(dataArray);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataArray.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDataArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemView v = (ItemView)convertView;
		if (v == null) {
			v = new ItemView(mContext);
			v.setOnImageClickListener(this);
		}
		v.setData(mDataArray.get(position));
		return v;
	}

	@Override
	public void onImageClick(NaverMovieItem data) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onImageClick(data);
		}
	}

}
