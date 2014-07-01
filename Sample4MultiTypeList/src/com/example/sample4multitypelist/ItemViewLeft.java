package com.example.sample4multitypelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewLeft extends FrameLayout {

	public ItemViewLeft(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView messageView;
	MyData mData;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_left_layout, this);
		iconView = (ImageView)findViewById(R.id.icon_view);
		messageView = (TextView)findViewById(R.id.message_view);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		iconView.setImageResource(data.resId);
		messageView.setText(data.message);
	}

}
