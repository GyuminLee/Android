package com.example.hellonetwork;

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

	NetworkRequest.OnProcessCompletedListener mCompleted = new NetworkRequest.OnProcessCompletedListener() {
		
		@Override
		public void onCompleted(NetworkRequest request) {
			// TODO Auto-generated method stub
			if (mRequest == request) {
				setImageBitmap((Bitmap)request.getResult());
				mRequest = null;
			}
		}
	};
	
	public void setImageURL(String url) {
		setImageResource(R.drawable.ic_launcher);
		if (mRequest != null) {
			mRequest.cancel();
			mRequest = null;
		}
		mRequest = new ImageRequest(url);
		NetworkModel.getInstance().getImageData(mRequest, mCompleted, mHandler);
		
	}

}
