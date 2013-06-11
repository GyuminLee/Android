package com.example.testnetworksample2;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRequest extends NetworkRequest {

	String imageUrl;
	
	public ImageRequest(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Override
	public URL getRequestURL() {
		try {
			URL url = new URL(imageUrl);
			return url;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	Bitmap mBitmap;
	
	@Override
	public void parsing(InputStream is) throws ParsingException {
		mBitmap = BitmapFactory.decodeStream(is);
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return mBitmap;
	}

}
