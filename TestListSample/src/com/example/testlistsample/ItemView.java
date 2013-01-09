package com.example.testlistsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {

	ImageView icon;
	TextView title;
	TextView description;
	MyData mData;

	OnImageClickListener mListener;
	
	public interface OnImageClickListener {
		public void onImageClick(MyData data);
	}
	
	public void setOnImageClickListener(OnImageClickListener listener) {
		mListener = listener;
	}
	
	public ItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_layout, this);
		icon = (ImageView)findViewById(R.id.icon);
		icon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onImageClick(mData);
				}
			}
		});
		title = (TextView)findViewById(R.id.title);
		description = (TextView)findViewById(R.id.description);
	}
	
	public void setData(MyData data) {
		mData = data;
		icon.setImageResource(data.imageId);
		title.setText(data.title);
		description.setText(data.description);
	}

}
