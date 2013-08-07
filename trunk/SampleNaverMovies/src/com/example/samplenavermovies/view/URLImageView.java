package com.example.samplenavermovies.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.samplenavermovies.R;
import com.example.samplenavermovies.model.ImageRequest;
import com.example.samplenavermovies.model.NetworkManager;
import com.example.samplenavermovies.model.NetworkRequest;

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
	
	
	public void setImageURL(String url) {
		if (mRequest != null) {
			mRequest.cancel();
			mRequest = null;
		}
		// default image
		setImageResource(R.drawable.ic_launcher);
		
		if (url == null || url.equals("")) return;
		
		mRequest = new ImageRequest(url);
		NetworkManager.getInstance().getImageData(mRequest, new NetworkRequest.OnCompletedListener() {
			
			@Override
			public void onSuccess(NetworkRequest request, Object result) {
				if (request == mRequest && result != null) {
					Bitmap bm = (Bitmap)result;
					setImageBitmap(bm);
					mRequest = null;
				}
			}
			
			@Override
			public void onFail(NetworkRequest request, int errorCode, String errorMsg) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

}
