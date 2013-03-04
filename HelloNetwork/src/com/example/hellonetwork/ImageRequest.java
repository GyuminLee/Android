package com.example.hellonetwork;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.graphics.Bitmap;

public class ImageRequest extends NetworkRequest {

	String urlString;
	Bitmap mBitmap;
	ArrayList<ImageRequest> notifyRequest = new ArrayList<ImageRequest>();
	
	public boolean addNotifyRequest(ImageRequest request) {
		if (urlString.equals(request.urlString)) {
			notifyRequest.add(request);
			return true;
		}
		return false;
	}
	public ImageRequest(String url) {
		this.urlString = url;
	}
	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
		NetworkModel.getInstance().remove(this);
	}

	@Override
	protected void parsing(InputStream is) {
		// TODO Auto-generated method stub
		mBitmap = ImageCache.getInstance().createBitmapFromNetwork(getKey(), is);
		for (ImageRequest rq : notifyRequest) {
			rq.setBitmapAndPost(mBitmap);
		}
	}
	
	public String getKey() {
		String key = null;
		key = URLEncoder.encode(urlString);
		return key;
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}
	
	public void setBitmapAndPost(Bitmap bitmap) {
		setBitmap(bitmap);
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mListener != null) {
						mListener.onCompleted(ImageRequest.this);
					}
				}
			});
		} else {
			if (mListener != null) {
				mListener.onCompleted(this);
			}
		}
	}
	
	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return mBitmap;
	}

}
