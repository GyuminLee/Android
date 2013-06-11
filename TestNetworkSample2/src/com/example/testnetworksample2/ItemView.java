package com.example.testnetworksample2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	URLImageView image;
	TextView title;
	TextView director;
	NaverMovieItem item;
	Handler mHandler = new Handler();
	ImageRequest mRequest;
	
	public ItemView(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_layout, this);
		image = (URLImageView)findViewById(R.id.image);
		title = (TextView)findViewById(R.id.title);
		director = (TextView)findViewById(R.id.director);
	}

	public void setItemData(NaverMovieItem item) {
		this.item = item;
		title.setText(item.title);
		director.setText(item.director);
		image.setImaegURL(item.image);
	}
}
