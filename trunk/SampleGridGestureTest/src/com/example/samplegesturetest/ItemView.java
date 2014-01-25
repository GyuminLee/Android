package com.example.samplegesturetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemView extends GestureItemViewGroup {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	TextView titleView;
	ImageView imageView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		imageView = (ImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		registerChildClickView(titleView);
//		registerChildClickView(imageView);
	}
	
	@Override
	public void onChildViewClick(View v) {
		super.onChildViewClick(v);
		if (v == titleView) {
			Toast.makeText(getContext(), "title click", Toast.LENGTH_SHORT).show();
		} else if (v == imageView) {
			Toast.makeText(getContext(), "image click", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void setText(String text) {
		titleView.setText(text);
	}

}
