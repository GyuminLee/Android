package com.example.sample3customlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView;
	TextView descView;
	MyData mData;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		descView = (TextView)findViewById(R.id.desc);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		titleView.setText(data.name);
		descView.setText(data.desc);
		iconView.setImageResource(data.resId);
	}
	
	public MyData getMyData() {
		return mData;
	}

}
