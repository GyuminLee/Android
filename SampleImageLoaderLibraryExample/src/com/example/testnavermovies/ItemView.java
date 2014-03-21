package com.example.testnavermovies;

import com.begentgroup.imageloader.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	ImageView iconView;
	TextView titleView;
	TextView subTitleView;
	MovieItem item;

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.row_layout, this);
		iconView = (ImageView)findViewById(R.id.imageView1);
		titleView = (TextView)findViewById(R.id.textView1);
		subTitleView = (TextView)findViewById(R.id.textView2);
	}
	
	public void setMovieItem(MovieItem item) {
		this.item = item;
		titleView.setText(item.title);
		subTitleView.setText(item.subtitle);
		ImageLoader.getInstance().displayImage(item.image, iconView);
	}

}
