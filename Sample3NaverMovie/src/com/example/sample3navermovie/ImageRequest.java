package com.example.sample3navermovie;

import android.graphics.Bitmap;
import android.os.Handler;

public class ImageRequest {
	String url;
	NetworkModel.OnImageLoadListener2 listener;
	Handler mHandler;
	Bitmap mBitmap;
	boolean isSync;
	
	public static final int TYPE_URL = 1;
	public static final int TYPE_EXIT = 2;
	
	int requestType = TYPE_URL;
	String mKey = null;
	public String getKey() {
		if (mKey == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("key_");
			int center = url.length() / 2;
			String str1 = url.substring(0, center);
			String str2 = url.substring(center + 1);
			sb.append(String.valueOf(str1.hashCode()));
			sb.append(String.valueOf(str2.hashCode()));
			mKey = sb.toString();
		}
		return mKey;
	}
	
	public void sendSuccess(Bitmap bitmap) {
		mBitmap = bitmap;
		if (mHandler != null && !isSync) {
			if (listener != null) {
				mHandler.post(new Runnable(){
					@Override
					public void run() {
						listener.onImageLoaded(ImageRequest.this, mBitmap);
					}
				});
			}
		} else {
			if (listener != null) {
				listener.onImageLoaded(this, mBitmap);
			}
		}
	}
	
	public void sendFail() {
		if (mHandler != null && !isSync) {
			if (listener != null) {
				mHandler.post(new Runnable(){
					@Override
					public void run() {
						listener.onImageLoadFail(ImageRequest.this);
					}
				});
			}
		} else {
			if (listener != null) {
				listener.onImageLoadFail(this);
			}
		}
	}
	
	boolean isCancel = false;
	public void cancel() {
		NetworkModel.getInstance().removeRequest(this);
		isCancel = true;
	}
}
