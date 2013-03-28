package com.example.samplelisttest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyItemView extends FrameLayout {

	TextView titleView;
	TextView descView;
	ImageView expandView;
	MyData mData;
	
	public interface OnDescClickListener {
		public void onDescClick(View v, MyData data);
	}
	
	OnDescClickListener mListener;
	
	public void setOnDescClickListener(OnDescClickListener listener) {
		mListener = listener;
	}
	
	public MyItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.my_list_item_view, this);
		titleView = (TextView)findViewById(R.id.title);
		descView = (TextView)findViewById(R.id.desc);
		expandView = (ImageView)findViewById(R.id.expand);
		expandView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (descView.getVisibility() == View.GONE) {
					descView.setVisibility(View.VISIBLE);
				} else {
					descView.setVisibility(View.GONE);
				}
			}
		});
		descView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onDescClick(MyItemView.this, mData);
				}
			}
		});
	}
	
	
	public void setData(MyData data) {
		mData = data;
		titleView.setText(data.title);
		descView.setText(data.desc);
		descView.setVisibility(View.GONE);
	}
	

}
