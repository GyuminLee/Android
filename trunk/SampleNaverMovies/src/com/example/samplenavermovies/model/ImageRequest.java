package com.example.samplenavermovies.model;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.graphics.Bitmap;

public class ImageRequest extends NetworkRequest {

	String mUrl;
	boolean addPendingRequest = true;
	
	ArrayList<ImageRequest> pendingRequest = new ArrayList<ImageRequest>();
	
	public ImageRequest(String url) {
		mUrl = url;
	}
	
	public boolean isPendingRequest(ImageRequest request) {
		if (request.mUrl.equals(mUrl) && addPendingRequest) {
			pendingRequest.add(request);
			return true;
		} else {
			return false;
		}
	}
	public String getKey() {
		try {
			String encodedKey =  URLEncoder.encode(mUrl, "utf-8");
			if (encodedKey.length() > 100) {
				encodedKey = (String)encodedKey.subSequence(encodedKey.length() - 100, encodedKey.length() - 1);
			}
			return encodedKey;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public URL getURL() {
		
		try {
			return new URL(mUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	Object parse(InputStream is) {
		Bitmap bm = CacheManager.getInstance().setFileCache(getKey(), is);
		return bm;
	}
	
	@Override
	protected void postProcess(Object result) {
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				addPendingRequest = false;
				for (int i = 0; i < pendingRequest.size(); i++) {
					ImageRequest ir = pendingRequest.get(i);
					ir.sendResult((Bitmap)getResult());
				}
			}
		});
	}
	
	
	@Override
	public boolean cancel() {
		if (pendingRequest.size() > 0) {
			setCancel(true);
		} else {
			super.cancel();
			NetworkManager.getInstance().removeImageRequest(this);
		}
		return true;
	}
	public void sendResult(Bitmap bm) {
		mResult = bm;
		if (!isCancel()) {
			mListener.onSuccess(this, bm);
		}
	}

}
