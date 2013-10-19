package com.example.samplecustomlisttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewRight extends FrameLayout {

	public ItemViewRight(Context context) {
		super(context);
		init(context);
	}
	
	ImageView iconView;
	TextView titleView;
	TextView descView;
	
	public void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.item_layout_right, this);
		iconView = (ImageView)findViewById(R.id.imageView1);
		titleView = (TextView)findViewById(R.id.textView1);
		descView = (TextView)findViewById(R.id.textView2);
	}
	
	public void setData(ItemData data) {
		iconView.setImageResource(data.imageId);
		titleView.setText(data.title);
		descView.setText(data.desc);
	}
}
