package com.example.samplenavermovies.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samplenavermovies.R;
import com.example.samplenavermovies.model.ItemData;
import com.example.samplenavermovies.model.NaverMovieItem;

public class ItemView extends FrameLayout {

	ImageView imageView;
	TextView movieTitleView;
	TextView authorView;
	NaverMovieItem mData;
	
	OnItemImageClickListener mListener;
	
	public interface OnItemImageClickListener {
		public void onItemImageClicked(View view, NaverMovieItem data);
	}
	
	public void setOnItemImageClickListener(OnItemImageClickListener listener) {
		mListener = listener;
	}
	
	public ItemView(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_view, this);
		imageView = (ImageView)findViewById(R.id.image);
		movieTitleView = (TextView)findViewById(R.id.movieTitle);
		authorView = (TextView)findViewById(R.id.author);
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onItemImageClicked(ItemView.this, mData);
				}
			}
		});
	}
	
	public void setItemData(NaverMovieItem data) {
		mData = data;
		movieTitleView.setText(data.title);
		authorView.setText(data.director);
	}

}
