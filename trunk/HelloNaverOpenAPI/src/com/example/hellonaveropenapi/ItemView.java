package com.example.hellonaveropenapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hellonaveropenapi.NetworkRequest.OnResultListener;

public class ItemView extends FrameLayout {

	URLImageView iconView;
	TextView titleView;
	TextView directorView;
	MovieItem mData;
	
	public ItemView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.item_layout, this);
		iconView = (URLImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		directorView = (TextView)findViewById(R.id.director);
	}
	
	ImageRequest request;
	public void setData(MovieItem data) {
		mData = data;
		titleView.setText(data.title);
		directorView.setText(data.director);
		iconView.setImageURL(data.image);
	}

}
