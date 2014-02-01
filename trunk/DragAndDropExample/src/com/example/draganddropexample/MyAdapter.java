package com.example.draganddropexample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	int[] imageList = { R.drawable.sample_0, R.drawable.sample_1,
			R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4,
			R.drawable.sample_5, R.drawable.sample_6, R.drawable.sample_7 };

	public MyAdapter(Context context) {
		mContext = context;
	}
	@Override
	public int getCount() {
		return imageList.length;
	}

	@Override
	public Object getItem(int position) {
		return (Integer)imageList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView v = (ImageView)convertView;
		if(v == null) {
			v = new ImageView(mContext);
			v.setScaleType(ImageView.ScaleType.FIT_XY);
		}
		v.setImageResource(imageList[position]);
		return v;
	}
}
