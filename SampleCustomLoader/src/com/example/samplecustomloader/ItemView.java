package com.example.samplecustomloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {

	ImageView icon;
	TextView title;
	ItemData mItem;
	
	public ItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.item_layout, this);
		icon = (ImageView)findViewById(R.id.imageView1);
		title = (TextView)findViewById(R.id.textView1);
	}
	
	public void setData(ItemData item) {
		mItem = item;
		icon.setImageDrawable(item.icon);
		title.setText(item.name);
	}

}
