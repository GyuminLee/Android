package org.tacademy.basic.samplelist2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements MyListItem.OnMyItemClickListener {

	Context mContext;
	MyData[] mData;
	
	public interface OnMyAdapterListener {
		public void onItemClick(MyData data);
	}
	
	OnMyAdapterListener mListener;
	
	public void setOnMyAdapterListener(OnMyAdapterListener listener) {
		mListener = listener;
	}	
	
	public MyAdapter(Context context,MyData[] data) {
		mContext = context;
		mData = data;
	}
	
	public int getCount() {
		return mData.length;
	}

	public Object getItem(int position) {
		return mData[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		MyListItem item = (MyListItem)convertView;
		if (item == null) {
			item = new MyListItem(mContext);
			item.setOnMyItemClickListener(this);
		}
		item.setData((MyData)getItem(position));
		
		return item;
	}

	public void onItemClick(MyData data) {
		if (mListener != null) {
			mListener.onItemClick(data);
		}
	}

}
