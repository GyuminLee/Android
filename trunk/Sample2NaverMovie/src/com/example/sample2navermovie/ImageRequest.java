package com.example.sample2navermovie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRequest extends NetworkRequest<Bitmap> {
	String imageUrl;
	
	public ImageRequest(String url) {
		imageUrl = url;
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

}
