package com.example.sample3gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {
	int[] resIdList;
	Context mContext;
	int width, height;
	
	public MyAdapter(Context context, int... resIdList) {
		this.resIdList = resIdList;
		mContext = context;
		width = (int)context.getResources().getDimension(R.dimen.gallery_image_width);
		height = (int)context.getResources().getDimension(R.dimen.gallery_image_height);
	}
	
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}
	@Override
	public Integer getItem(int position) {
		return (Integer)resIdList[position % resIdList.length];
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
		} else {
			imageView = (ImageView)convertView;
		}
		imageView.setImageResource(resIdList[position % resIdList.length]);
		imageView.setLayoutParams(new Gallery.LayoutParams(width, height));
		
		return imageView;
	}
	
}
