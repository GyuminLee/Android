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
	
	ArrayList<ImageRequest> pendingRequest = new ArrayList<ImageRequest>();
	
	public ImageRequest(String url) {
		mUrl = url;
	}
	
	public boolean isPendingRequest(ImageRequest request) {
		if (request.mUrl.equals(mUrl)) {
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
	public void process(InputStream is) {
		super.process(is);
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < pendingRequest.size(); i++) {
					ImageRequest ir = pendingRequest.get(i);
					ir.sendResult((Bitmap)getResult());
				}
			}
		});
	}
	
	
	public void sendResult(Bitmap bm) {
		mResult = bm;
		mListener.onSuccess(this, bm);
	}

}
