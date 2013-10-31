package com.example.hellocustomlisttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyItemRightView extends FrameLayout {

	ImageView iconView;
	TextView nameView;
	TextView ageView;
	MyData mData;
	
	public MyItemRightView(Context context) {
		super(context);
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout_right, this);
		iconView = (ImageView)findViewById(R.id.imageView1);
		nameView = (TextView)findViewById(R.id.textView1);
		ageView = (TextView)findViewById(R.id.textView2);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		iconView.setImageResource(data.resId);
		nameView.setText(data.name);
		ageView.setText("age : " + data.age);
	}
	
}
