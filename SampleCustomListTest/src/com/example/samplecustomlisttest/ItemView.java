package com.example.samplecustomlisttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init(context);
	}

	ImageView iconView;
	TextView titleView;
	TextView descView;
	ItemData mData;
	
	public interface OnItemImageClickListener {
		public void onItemImageClicked(ItemView v, ItemData item);
	}
	
	OnItemImageClickListener mListener;
	public void setOnItemImageClickListener(OnItemImageClickListener listener) {
		mListener = listener;
	}
	
	public void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		descView = (TextView)findViewById(R.id.description);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onItemImageClicked(ItemView.this, mData);
				}
				
			}
		});
	}
	
	public void setData(ItemData data) {
		mData = data;
		titleView.setText(data.title);
		descView.setText(data.desc);
		iconView.setImageResource(data.imageId);
	}
}
