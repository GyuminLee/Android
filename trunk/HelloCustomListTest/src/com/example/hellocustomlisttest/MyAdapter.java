package com.example.hellocustomlisttest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements MyItemView.OnItemImageClickListener {

	Context mContext;
	ArrayList<MyData> items = new ArrayList<MyData>();

	public interface OnAdapterItemClickListener {
		public void onAdapterItemClick(MyAdapter adapter, View view, MyData data);
	}
	
	OnAdapterItemClickListener mListener;
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
		mListener = listener;
	}
	
	public MyAdapter(Context context, ArrayList<MyData> list) {
		mContext = context;
		items.addAll(list);
	}

	public void add(MyData data) {
		items.add(data);
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
		MyItemView view;
		if (convertView == null) {
			view = new MyItemView(mContext);
			view.setOnItemImageClickListener(this);
		} else {
			view = (MyItemView) convertView;
		}
		view.setMyData(items.get(position));
		return view;
	}

	@Override
	public void onItemImageClick(MyItemView view, MyData data) {
		if (mListener != null) {
			mListener.onAdapterItemClick(this, view, data);
		}
	}

}
