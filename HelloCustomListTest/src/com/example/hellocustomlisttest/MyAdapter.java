package com.example.hellocustomlisttest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements MyItemView.OnItemImageClickListener {

	Context mContext;
	ArrayList<MyData> items = new ArrayList<MyData>();
	public static final int VIEW_TYPE_COUNT = 2;

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
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}
	
	final static int TYPE_LEFT = 0;
	final static int TYPE_RIGHT = 1;
	
	@Override
	public int getItemViewType(int position) {
		if (position % 2 == 0) return TYPE_LEFT;
		return TYPE_RIGHT;
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
		switch(getItemViewType(position)) {
		case TYPE_LEFT :
			MyItemView view;
			if (convertView == null) {
				view = new MyItemView(mContext);
				view.setOnItemImageClickListener(this);
			} else {
				view = (MyItemView) convertView;
			}
			view.setMyData(items.get(position));
			return view;
		case TYPE_RIGHT :
			MyItemRightView view2;
			if (convertView == null) {
				view2 = new MyItemRightView(mContext);
			} else {
				view2 = (MyItemRightView)convertView;
			}
			view2.setMyData(items.get(position));
			return view2;
		}
		return null;
	}

	@Override
	public void onItemImageClick(MyItemView view, MyData data) {
		if (mListener != null) {
			mListener.onAdapterItemClick(this, view, data);
		}
	}

}
