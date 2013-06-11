package com.example.testnetworksample2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class URLImageView extends ImageView {

	ImageRequest mRequest;
	Handler mHandler = new Handler();
	
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
	
	public void setImaegURL(String image) {
		mRequest = null;
		if (image != null && !image.equals("")) {
			mRequest = new ImageRequest(image);
			NetworkModel.getInstance().getNetworkData(mRequest,
					new NetworkRequest.OnProcessCompleteListener() {

						@Override
						public void onError(NetworkRequest request,
								String errorMessage) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onCompleted(NetworkRequest request) {
							if (mRequest == request) {
								Bitmap bm = (Bitmap) request.getResult();
								setImageBitmap(bm);
							}
						}
					}, mHandler);
		}
	}

}
