package com.example.testcustomlistsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyItemSendView extends FrameLayout {

	ImageView iconView;
	TextView nameView;
	TextView descView;
	MyData mData;
	
	public MyItemSendView(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_send, this);
		iconView = (ImageView)findViewById(R.id.imageView1);
		nameView = (TextView)findViewById(R.id.textView1);
		descView = (TextView)findViewById(R.id.textView2);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		nameView.setText(data.name);
		descView.setText(data.desc);
	}

}
