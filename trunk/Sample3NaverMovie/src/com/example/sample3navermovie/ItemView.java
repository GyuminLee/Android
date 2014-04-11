package com.example.sample3navermovie;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}
	ImageView iconView;
	TextView titleView;
	TextView directorView;
	MovieItem item;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.iconView);
		titleView = (TextView)findViewById(R.id.titleView);
		directorView = (TextView)findViewById(R.id.directorView);
	}
	
	public void setMovieItem(MovieItem item) {
		this.item = item;
		titleView.setText(Html.fromHtml(item.title));
		directorView.setText(item.director);
	}

}
