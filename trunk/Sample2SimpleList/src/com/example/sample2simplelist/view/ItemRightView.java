package com.example.sample2simplelist.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample2simplelist.R;
import com.example.sample2simplelist.model.MyData;

public class ItemRightView extends FrameLayout {

	ImageView iconView;
	TextView titleView;
	TextView descView;
	MyData mData;
	
	public ItemRightView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_right_layout, this);
		iconView = (ImageView)findViewById(R.id.iconView);
		titleView = (TextView)findViewById(R.id.titleView);
		descView = (TextView)findViewById(R.id.descView);
	}

	public void setMyData(MyData data) {
		iconView.setImageResource(data.imageId);
		titleView.setText(data.title);
		descView.setText(data.description);
	}
}
