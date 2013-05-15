package com.example.googlemaptest.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

public class ImageManager {

	private static ImageManager instance;
	
	private Handler mHandler;
	
	private final static int MAX_CACHE_SIZE = 100;
	
	private static final int MAX_THREAD_COUNT = 5;
	
	private HashMap<String,Bitmap> cache = new HashMap<String,Bitmap>();
	
	private ArrayList<ImageRequest> requests = new ArrayList<ImageRequest>();
	
	private boolean isRunning = false;
	
	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}
	
	private ImageManager() {
		isRunning = true;
		mHandler = new Handler();
		for (int i = 0; i < MAX_THREAD_COUNT;i++) {
			(new ImageProcessThread()).start();
		}
	}
	
	public synchronized boolean remove(ImageRequest request) {
		return requests.remove(request);
	}

	public synchronized boolean enqueue(ImageRequest request) {
		requests.add(request);
		notifyAll();
		return true;
	}
	
	public synchronized ImageRequest dequeue() {
		ImageRequest request = null;

		if (requests.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
		if (requests.size() > 0)
			request = requests.remove(0);			
		
		return request;
	}
	
	public synchronized void stop() {
		isRunning = false;
	}
	
	public Bitmap isContainsKey(String key) {
		Bitmap bitmap = null;
		Iterator<String> iter = cache.keySet().iterator();
		while(iter.hasNext()) {
			String keyValue = (String)iter.next();
			if (key.equals(keyValue)) {
				bitmap = cache.get(keyValue);
				return bitmap;
			}
		}
		return bitmap;
	}
	
	public void setCache(String key, Bitmap bitmap) {
		if (cache.size() > MAX_CACHE_SIZE - 1) {
			Iterator iter = cache.keySet().iterator();
			int removesize = cache.size() - MAX_CACHE_SIZE + 1;
			while(iter.hasNext() && (removesize > 0)) {
				String keyValue = (String)iter.next();
				cache.remove(keyValue);
				removesize--;
			}
		}
		cache.put(key,bitmap);
	}
	
	class ImageProcessThread extends Thread {

		@Override
		public void run() {
			while(isRunning) {
				ImageRequest request = dequeue();
				if (request == null) continue;
				try {
					Bitmap bmp;
					if ((bmp = isContainsKey(request.urlString))!=null) {
						request.process(mHandler,bmp);
					} else if (FileManager.getInstance().exists(request.getFilePath())){
						InputStream is = FileManager.getInstance().getInputStream(request.getFilePath()); 
						makeBitmap(request,is);
					} else {
						URL url = request.getRequestUrl();
						
						HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	
						int responseCode = httpConnection.getResponseCode();
						
						if (responseCode == HttpURLConnection.HTTP_OK) {
							InputStream in = httpConnection.getInputStream();
							
							InputStream fis = FileManager.getInstance().saveCache(request.getFilePath(),in);
							
							makeBitmap(request,fis);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
		}
		
		private void makeBitmap(ImageRequest request,InputStream is) {
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			setCache(request.urlString,bitmap);
			request.process(mHandler, bitmap);
		}
		
	}

	
}
