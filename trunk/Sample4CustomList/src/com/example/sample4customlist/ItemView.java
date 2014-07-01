package com.example.sample4customlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	public interface OnItemDataClickListener {
		public void onLikeClick(View v,MyData data);
	}
	
	OnItemDataClickListener mListener;
	public void setOnItemDataClickListener(OnItemDataClickListener listener) {
		mListener = listener;
	}
	
	ImageView iconView;
	TextView nameView;
	TextView contentView;
	TextView likeView;
	TextView commentView;
	
	MyData mData;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_view_layout, this);
		iconView = (ImageView)findViewById(R.id.icon_view);
		nameView = (TextView)findViewById(R.id.name_view);
		contentView = (TextView)findViewById(R.id.content_view);
		likeView = (TextView)findViewById(R.id.like_view);
		likeView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onLikeClick(ItemView.this, mData);
				}
			}
		});
		commentView = (TextView)findViewById(R.id.comment_view);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		iconView.setImageResource(data.resId);
		nameView.setText(data.name);
		contentView.setText(data.content);
	}

}
