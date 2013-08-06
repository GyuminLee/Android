package com.example.samplenavermovies;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.example.samplenavermovies.model.ItemData;
import com.example.samplenavermovies.view.ItemView;

public class MovieAdapter extends BaseAdapter implements ItemView.OnItemImageClickListener {
	Context mContext;
	ArrayList<ItemData> mItemList;
	OnAdapterImageClickListener mListener;
	
	public interface OnAdapterImageClickListener {
		public void onAdapterImageClick(Adapter adapter, View view, ItemData data);
	}
	
	public void setOnAdapterImageClickListener(OnAdapterImageClickListener listener) {
		mListener = listener;
	}
	
	public MovieAdapter(Context context) {
		mContext = context;
		mItemList = new ArrayList<ItemData>();
	}
	
	public void add(ItemData data) {
		mItemList.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<ItemData> list) {
		mItemList.addAll(list);
		notifyDataSetChanged();		
	}
	
	public void clear() {
		mItemList.clear();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItemList.size();
	}

	@Override
	public ItemData getItem(int position) {
		// TODO Auto-generated method stub
		return mItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view;
		
		if (convertView == null) {
			view = new ItemView(mContext);
			view.setOnItemImageClickListener(this);
		} else {
			view = (ItemView)convertView;
		}
		view.setItemData(getItem(position));
		return view;
	}

	@Override
	public void onItemImageClicked(View view, ItemData data) {
		if (mListener != null) {
			mListener.onAdapterImageClick(this, view, data);
		}
	}

}
