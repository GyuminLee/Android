package com.example.hellonaveropenapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRequest extends NetworkRequest<Bitmap> {

	String imageUrl;
	ArrayList<ImageRequest> mSameList = new ArrayList<ImageRequest>();
	
	
	public boolean isSameRequest(ImageRequest request) {
		if (imageUrl.equals(request.imageUrl)) {
			mSameList.add(request);
			return true;
		}
		return false;
	}
	
	public ImageRequest(String urlString) {
		imageUrl = urlString;
	}
	@Override
	public URL getURL() {
		URL url = null;
		try {
			url = new URL(imageUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

	
	@Override
	protected Bitmap parsing(InputStream is) {
		InputStream fis;
		try {
			fis = NetworkModel.getInstance().saveFile(getKey(),is);
			Bitmap bm = BitmapFactory.decodeStream(fis);
			NetworkModel.getInstance().setCache(getKey(), bm);
			return bm;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getKey() {
		try {
			return URLEncoder.encode(imageUrl,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	public void setResult(Bitmap bm) {
		mResult = bm;
	}
	boolean isCancelable = false;
	
	@Override
	public void setCancel() {
		if (mSameList.size() == 0) {
			super.setCancel();
			NetworkModel.getInstance().removeImageRequest(this);
		} else {
			isCancelable = true;
		}
	}
	
	@Override
	public void sendResult() {
		if (isCancelable) {
			super.isCancel();
		}
		super.sendResult();
		for (ImageRequest request : mSameList) {
			if (!request.isCanceled) {
				request.setResult(mResult);
				request.sendResult();
			}
		}
	}
	
	@Override
	public void sendError(int code) {
		super.sendError(code);
		for (ImageRequest request : mSameList) {
			if (!request.isCanceled) {
				request.sendError(code);
			}
		}
	}
}
