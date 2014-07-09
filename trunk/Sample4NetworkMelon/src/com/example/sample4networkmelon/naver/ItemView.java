package com.example.sample4networkmelon.naver;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample4networkmelon.R;
import com.example.sample4networkmelon.entity.MovieItem;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView titleView;
	TextView directorView;
	MovieItem mItem;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.naver_movie_item, this);
		iconView = (ImageView)findViewById(R.id.icon_view);
		titleView = (TextView)findViewById(R.id.title_view);
		directorView = (TextView)findViewById(R.id.director_view);
	}
	
	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		directorView.setText(item.director);
	}

}
