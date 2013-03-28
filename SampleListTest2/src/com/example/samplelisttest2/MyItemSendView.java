package com.example.samplelisttest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MyItemSendView extends FrameLayout {

	TextView titleView;
	MyData mData;
	
	public MyItemSendView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.my_list_item_send, this);
		titleView = (TextView)findViewById(R.id.title);
	}
	
	public void setData(MyData data) {
		mData = data;
		titleView.setText(data.title);
	}
}
