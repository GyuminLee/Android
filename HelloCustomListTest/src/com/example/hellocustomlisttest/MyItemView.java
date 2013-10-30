package com.example.hellocustomlisttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyItemView extends FrameLayout {

	ImageView iconView;
	TextView nameView;
	TextView ageView;
	MyData mData;
	
	public MyItemView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.iconView);
		nameView = (TextView)findViewById(R.id.nameView);
		ageView = (TextView)findViewById(R.id.ageView);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		iconView.setImageResource(data.resId);
		nameView.setText(data.name);
		ageView.setText("age : " + data.age);
	}
 
}
