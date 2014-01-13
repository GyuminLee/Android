package com.example.sample2navermovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

public class URLImageView extends ImageView {

	public URLImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public URLImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public URLImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	ImageRequest mRequest;
	
	public void setImageURL(String url) {
		mRequest = null;
		if (url != null && !url.equals("")) {
			mRequest = new ImageRequest(url);
			mRequest.setOnResultListener(new NetworkRequest.OnResultListener<Bitmap>() {

				@Override
				public void onSuccess(NetworkRequest request, Bitmap result) {
					if (request == mRequest) {
						setImageBitmap(result);
					}
				}

				@Override
				public void onError(NetworkRequest request, int error) {
					
				}
			});
			NetworkModel.getInstance().getNetworkImage(mRequest);
		}
	}

}
