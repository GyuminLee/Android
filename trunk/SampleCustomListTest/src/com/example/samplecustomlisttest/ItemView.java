package com.example.samplecustomlisttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init(context);
	}

	ImageView iconView;
	TextView titleView;
	TextView descView;
	ItemData mData;
	
	public void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		descView = (TextView)findViewById(R.id.description);
	}
	
	public void setData(ItemData data) {
		mData = data;
		titleView.setText(data.title);
		descView.setText(data.desc);
		iconView.setImageResource(data.imageId);
	}
}
