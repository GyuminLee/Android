package com.example.sample3multiitemlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class RightItemView extends FrameLayout {

	public RightItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView contentView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.right_item_layout, this);
		iconView = (ImageView)findViewById(R.id.imageView1);
		contentView = (TextView)findViewById(R.id.textView1);
	}
	
	public void setMyData(MyData data) {
		iconView.setImageResource(data.resId);
		contentView.setText(data.content);
	}

}
