package com.example.sample3navermovie;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView;
	TextView directorView;
	MovieItem mItem;

	ImageLoader imageLoader;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView) findViewById(R.id.iconView);
		titleView = (TextView) findViewById(R.id.titleView);
		directorView = (TextView) findViewById(R.id.directorView);
		imageLoader = ImageLoader.getInstance();
	}

	ImageRequest mRequest = null;
	
	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		directorView.setText(item.director);
		imageLoader.displayImage(item.image, iconView);
//		iconView.setImageResource(R.drawable.ic_stub);
//		if (item.image != null) {
//			if (mRequest != null) {
//				mRequest.cancel();
//				mRequest = null;
//			}
//			mRequest = new ImageRequest();
//			mRequest.url = item.image;
//			NetworkModel.getInstance().getImageBitmap(mRequest, new OnImageLoadListener2() {
//				
//				@Override
//				public void onImageLoaded(ImageRequest request, Bitmap bitmap) {
//					if (request == mRequest) {
//						iconView.setImageBitmap(bitmap);
//					}
//					mRequest = null;
//				}
//				
//				@Override
//				public void onImageLoadFail(ImageRequest request) {
//					iconView.setImageResource(R.drawable.ic_error);
//					mRequest = null;
//				}
//			});
//		} else {
//			iconView.setImageResource(R.drawable.ic_empty);
//		}
	}

}
