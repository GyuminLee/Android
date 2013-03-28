package com.example.samplelisttest2;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements MyItemView.OnDescClickListener {

	public static final int ITEM_VIEW_COUNT = 2;
	public static final int ITEM_VIEW_TYPE_ONE = 0;
	public static final int ITEM_VIEW_TYPE_SEND = 1;
	
	List<MyData> mList;
	Context mContext;
	public interface OnItemViewClickListener {
		public void onItemViewClick(Adapter a, View v,MyData data);
	}
	
	OnItemViewClickListener mListener;
	public void setOnItemViewClickListener(OnItemViewClickListener listener) {
		mListener = listener;
	}
	
	public MyAdapter(Context context, List<MyData> list) {
		mContext = context;
		mList = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyData data = mList.get(position);
		if (data.isSend == false) {
			MyItemView view;
			
			if (convertView == null) {
				view = new MyItemView(mContext);
				view.setOnDescClickListener(this);
			} else {
				view = (MyItemView)convertView;
			}
			
			view.setData(data);
			return view;
		} else {
			MyItemSendView view;
			if (convertView == null) {
				view = new MyItemSendView(mContext);
			} else {
				view = (MyItemSendView)convertView;
			}
			view.setData(data);
			return view;
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return ITEM_VIEW_COUNT;
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		MyData data = mList.get(position);
		if (data.isSend == true) {
			return ITEM_VIEW_TYPE_SEND;
		} else {
			return ITEM_VIEW_TYPE_ONE;
		}
	}
	
	public void add(MyData myData) {
		// TODO Auto-generated method stub
		mList.add(myData);
		notifyDataSetChanged();
	}

	@Override
	public void onDescClick(View v, MyData data) {
		// TODO Auto-generated method stub
		// process...
		if (mListener != null) {
			mListener.onItemViewClick(this, v, data);
		}
	}

}
