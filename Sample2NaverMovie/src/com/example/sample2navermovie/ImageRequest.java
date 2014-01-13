package com.example.sample2navermovie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRequest extends NetworkRequest<Bitmap> {
	String imageUrl;
	
	public ImageRequest(String url) {
		imageUrl = url;
	}
	
	public String getKey() {
		String key = null;
		try {
			key = URLEncoder.encode(imageUrl,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}
	@Override
	public URL getURL() throws MalformedURLException,
			UnsupportedEncodingException {
		return new URL(imageUrl);
	}

	@Override
	public void process(InputStream is) {
		result = BitmapFactory.decodeStream(is);
	}
	
	public void setResult(Bitmap bitmap) {
		result = bitmap;
	}
	
	@Override
	public void cancel() {
		super.cancel();
		NetworkModel.getInstance().remove(this);
	}

}
