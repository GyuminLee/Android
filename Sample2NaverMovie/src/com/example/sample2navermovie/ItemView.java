package com.example.sample2navermovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	ImageView iconView;
	TextView titleView;
	TextView actorView;
	MovieItem mData;
	
	public ItemView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.iconView);
		titleView = (TextView)findViewById(R.id.titleView);
		actorView = (TextView)findViewById(R.id.actorView);
	}
	
	public void setMovieItem(MovieItem item) {
		titleView.setText(Html.fromHtml(item.title));
		actorView.setText(item.actor);
		iconView.setImageResource(R.drawable.ic_launcher);
		if (item.image != null && !item.image.equals("")) {
			ImageRequest request = new ImageRequest(item.image);
			request.setOnResultListener(new NetworkRequest.OnResultListener<Bitmap>() {
				@Override
				public void onSuccess(NetworkRequest request, Bitmap result) {
					iconView.setImageBitmap(result);
				}
				@Override
				public void onError(NetworkRequest request, int error) {
					
				}
			});
			NetworkModel.getInstance().getNetworkImage(request);
		}
		mData = item;
	}

}
