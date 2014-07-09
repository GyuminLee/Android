package com.example.sample4networkmelon.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

public class ImageManager {
	private static ImageManager instance;
	Handler mHandler;
	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}
	
	private ImageManager() {
		mHandler = new Handler(Looper.getMainLooper());
	}
	
	public interface OnImageCompleteListener {
		public void onImageCompltete(String url, Bitmap bitmap);
	}
	
	public void getImage(String url, OnImageCompleteListener listener) {
		new Thread(new ImageDownloaderRunnable(url, listener)).start();
	}
	
	class ImageDownloaderRunnable implements Runnable {
		String urlString;
		OnImageCompleteListener mListener;
		
		public ImageDownloaderRunnable(String url, OnImageCompleteListener listener) {
			urlString = url;
			mListener = listener;
		}
		
		@Override
		public void run() {
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					final Bitmap bm = BitmapFactory.decodeStream(is);
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							if (mListener != null) {
								mListener.onImageCompltete(urlString, bm);
							}
						}
					});
					return;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if (mListener != null) {
						mListener.onImageCompltete(urlString, null);
					}
				}
			});
		}
	}
}
