package com.example.sample4gallery;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {

	ArrayList<Integer> mItems = new ArrayList<Integer>();
	Context mContext;
	int mPhotoWidth, mPhotoHeight;
	
	public MyAdapter(Context context) {
		mContext = context;
		
		mPhotoWidth = (int)context.getResources().getDimension(R.dimen.photo_width);
		mPhotoHeight = (int)context.getResources().getDimension(R.dimen.photo_height);
	}
	
	public void add(int resId) {
		mItems.add((Integer)resId);
		notifyDataSetChanged();
	}
	
	public int getItemCount() {
		return mItems.size();
	}
	
	@Override
	public int getCount() {
		return mItems.size() == 0?0:Integer.MAX_VALUE;
	}

	@Override
	public Integer getItem(int position) {
		return mItems.get(position % mItems.size());
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView v;
		if (convertView == null) {
			v = new ImageView(mContext);
			v.setLayoutParams(new Gallery.LayoutParams(mPhotoWidth, mPhotoHeight));
		} else {
			v = (ImageView)convertView;
		}
		v.setImageResource(mItems.get(position % mItems.size()));
		return v;
	}

}
