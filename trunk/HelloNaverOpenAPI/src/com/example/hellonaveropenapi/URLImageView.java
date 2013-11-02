package com.example.hellonaveropenapi;

import com.example.hellonaveropenapi.NetworkRequest.OnResultListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

public class URLImageView extends ImageView {

	public URLImageView(Context context) {
		super(context);
	}

	public URLImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public URLImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	ImageRequest request;
	public void setImageURL(String url) {
		if (request != null) {
			request.setCancel();
			request = null;
		}
		setImageResource(R.drawable.ic_launcher);
		request = new ImageRequest(url);
		request.setOnResultListener(new OnResultListener<Bitmap>() {

			@Override
			public void onSuccess(NetworkRequest req, Bitmap result) {
				if (request == req) {
					setImageBitmap(result);
					request = null;
				}
			}

			@Override
			public void onError(NetworkRequest req, int code) {
				
			}
		});
		
		NetworkModel.getInstance().getImageData(getContext(), request);
	}
}
