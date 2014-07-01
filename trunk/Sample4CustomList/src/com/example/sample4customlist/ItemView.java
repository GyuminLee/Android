package com.example.sample4customlist;

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
	TextView nameView;
	TextView contentView;
	MyData mData;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_view_layout, this);
		iconView = (ImageView)findViewById(R.id.icon_view);
		nameView = (TextView)findViewById(R.id.name_view);
		contentView = (TextView)findViewById(R.id.content_view);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		iconView.setImageResource(data.resId);
		nameView.setText(data.name);
		contentView.setText(data.content);
	}

}
