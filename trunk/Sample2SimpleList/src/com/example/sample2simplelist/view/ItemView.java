package com.example.sample2simplelist.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample2simplelist.R;
import com.example.sample2simplelist.model.MyData;

public class ItemView extends FrameLayout implements Checkable {
	ImageView iconView;
	TextView titleView;
	TextView descView;
	TextView detailView;
	MyData mData;
	boolean isChecked = false;
	
	public interface OnImageClickListener {
		public void onImageClick(View v,MyData d);
	}
	
	OnImageClickListener mListener;
	
	public void setOnImageClickListener(OnImageClickListener listener) {
		mListener = listener;
	}
	
	public ItemView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
//		Context context = getContext();
//		LayoutInflater inflater = LayoutInflater.from(context);
//		inflater.inflate(R.layout.item_layout, this);
		LayoutInflater.from(getContext()).inflate(R.layout.item2_layout, this);
		iconView = (ImageView)findViewById(R.id.iconView);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onImageClick(ItemView.this, mData);
				}
				
			}
		});
		titleView = (TextView)findViewById(R.id.titleView);
		descView = (TextView)findViewById(R.id.descView);
		detailView = (TextView)findViewById(R.id.detailView);
		descView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (detailView.getVisibility() == View.GONE) {
					detailView.setVisibility(View.VISIBLE);
				} else {
					detailView.setVisibility(View.GONE);
				}
				
			}
		});
	}

	public void setMyData(MyData data) {
		iconView.setImageResource(data.imageId);
		titleView.setText(data.title);
		descView.setText(data.description);
		detailView.setVisibility(View.GONE);
		mData = data;
		isChecked = false;
		setSelectedColor();
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		if (isChecked == checked) return;
		isChecked = checked;
		setSelectedColor();
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		setSelectedColor();
	}
	
	private void setSelectedColor() {
		if (isChecked) {
			setBackgroundColor(Color.DKGRAY);
		} else {
			setBackgroundColor(Color.WHITE);
		}
	}
}
