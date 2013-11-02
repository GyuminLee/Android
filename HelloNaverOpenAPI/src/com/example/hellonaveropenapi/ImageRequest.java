package com.example.hellonaveropenapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageRequest extends NetworkRequest<Bitmap> {

	String imageUrl;
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
	
	@Override
	public void setCancel() {
		super.setCancel();
		NetworkModel.getInstance().removeImageRequest(this);
	}
}
