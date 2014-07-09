package com.example.sample4networkmelon.naver;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample4networkmelon.R;
import com.example.sample4networkmelon.entity.MovieItem;
import com.example.sample4networkmelon.model.ImageManager;
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
	TextView directorView;
	MovieItem mItem;

	ImageLoader loader;
	DisplayImageOptions options;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.naver_movie_item,
				this);
		iconView = (ImageView) findViewById(R.id.icon_view);
		titleView = (TextView) findViewById(R.id.title_view);
		directorView = (TextView) findViewById(R.id.director_view);
		loader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(50))
		.build();
	}

	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		directorView.setText(item.director);
		loader.displayImage(item.image, iconView, options);
//		if (item.image != null && !item.image.equals("")) {
//			iconView.setImageResource(R.drawable.ic_stub);
//			ImageManager.getInstance().getImage(item.image,
//					new ImageManager.OnImageCompleteListener() {
//
//						@Override
//						public void onImageCompltete(String url, Bitmap bitmap) {
//							if (mItem.image != null && !mItem.image.equals("") &&
//									!mItem.image.equals(url)) return;
//							if (bitmap != null) {
//								iconView.setImageBitmap(bitmap);
//							} else {
//								iconView.setImageResource(R.drawable.ic_error);
//							}
//						}
//					});
//		} else {
//			iconView.setImageResource(R.drawable.ic_empty);
//		}
	}

}
