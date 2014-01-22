package com.example.sample2gallerytest;

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
	
	ImageView iv;
	TextView tv;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iv = (ImageView)findViewById(R.id.imageView1);
		tv = (TextView)findViewById(R.id.textView1);
	}
	
	public void setMyData(MyData data) {
		iv.setImageResource(data.imageId);
		tv.setText(data.title);
	}

}
