package com.example.samplenavermovie3;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView titleView;
	TextView actorView;
	ImageLoader loader;
	DisplayImageOptions options;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		actorView = (TextView)findViewById(R.id.actor);
		loader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(25))
		.build();
		
	}
	
	MovieItem mItem;
	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		actorView.setText(item.actor);
		// iconView...
		loader.displayImage(item.image, iconView,options);
	}

}
