package com.begentgroup.imageloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

public class ImageLoader {

	public static final int DEFAULT_TIMEOUT = 10000;
	private int timeout = DEFAULT_TIMEOUT;
	private static ImageLoader instance;
	private Map<Object,ImageRequest> mRequestMap = new HashMap<Object,ImageRequest>();

	private static final String TAG = "ImageLoader";
	
	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}

	private Configuration configuration;
	private Handler mMainHandler;
	private int threadCount;

	private ImageLoader() {
		mMainHandler = new Handler(Looper.getMainLooper());
		threadCount = Configuration.DEFAULT_THREAD_COUNT;
		for (int i = 0; i < threadCount; i++) {
			startLoop();
		}
	}

	List<ImageRequest> mWaitingQueue = new ArrayList<ImageRequest>();
	List<ImageRequest> mRunningQueue = new ArrayList<ImageRequest>();

	public void initialize(Configuration configuration) {
		this.configuration = configuration;
		if (threadCount < configuration.threadCount) {
			int startThreadCount = configuration.threadCount - threadCount;
			for (int i = 0; i < startThreadCount; i++) {
				startLoop();
			}
		} else if (threadCount > configuration.threadCount) {
			int endThreadCount = threadCount - configuration.threadCount;
			for (int i = 0; i < endThreadCount; i++) {
				endLoop();
			}
		}
	}

	private void endLoop() {
		ImageRequest request = new ImageRequest();
		request.type = ImageRequest.TYPE_END_LOOP;
		enqueue(request);
	}

	private void startLoop() {
		new Thread(new ImageDownloader()).start();
	}

	public ImageRequest displayImage(String url, ImageView imageView) {
		return displayImage(url, imageView, null, null, false, null);
	}

	public ImageRequest displayImage(String url, ImageView imageView, Configuration.Options options) {
		return displayImage(url, imageView, null, null, false, options);
	}
	
	public ImageRequest displayImage(String url, ImageView imageView, ImageRequest.BitmapBinder binder) {
		return displayImage(url, imageView, binder, null, false, null);
	}

	public ImageRequest displayImage(String url, ImageView imageView, ImageRequest.BitmapBinder binder, Configuration.Options options) {
		return displayImage(url, imageView, binder, null, false, options);
	}
	
	public ImageRequest displayImage(String url, ImageView imageView, ImageListener listener) {
		return displayImage(url, imageView, null, listener, false, null);
	}

	public ImageRequest displayImage(String url, ImageView imageView, ImageListener listener, Configuration.Options options) {
		return displayImage(url, imageView, null, listener, false, options);
	}
	
	public ImageRequest displayImage(String url, ImageView imageView, ImageRequest.BitmapBinder binder, ImageListener listener) {
		return displayImage(url, imageView, binder, listener, false, null);
	}

	public ImageRequest displayImage(String url, ImageView imageView, ImageRequest.BitmapBinder binder, ImageListener listener, Configuration.Options options) {
		return displayImage(url, imageView, binder, listener, false, options);
	}
	
	public ImageRequest displayImage(String url, Object target, ImageRequest.BitmapBinder binder, ImageListener listener, boolean isSync, Configuration.Options options) {
		ImageRequest request = new ImageRequest();
		request.imageUrl = url;
		request.target = target;
		request.binder = binder;
		request.mListener = listener;
		request.bNoUseThread = isSync;
		if (options == null) {
			options = (configuration != null) ? configuration.defaultOptions : null;
		}
		request.options = options;
		
		displayImage(request);
		return request;
	}
	
	private void sendBitmap(ImageRequest request,Bitmap bm) {
		request.resultBitmap = bm;
		request.resultCode = ImageRequest.RESULT_CODE_OK;
		request.bNoUseThread = true;
		if (isMainThread()) {
			request.run();
		} else {
			mMainHandler.post(request);
		}
	}
	
	public void displayImage(ImageRequest request) {
		if (request.imageUrl == null || request.imageUrl.equals("")) {
			// default Image Setting
			Bitmap bm = null;
			if (request.options != null) {
				bm = request.options.defaultImage;
			}
			sendBitmap(request,bm);
			return;
		}
		if (configuration != null) {
			Bitmap bm = configuration.cacheManager.getBitmap(request.imageUrl);
			if (bm != null) {
				sendBitmap(request,bm);
				return;
			}
		}
		// Not Cached
		if (request.bNoUseThread) {
			// Sync
			if (!isMainThread()) {
				// No Main Thread
				processRequest(request);
				return;
			}
			// disable sync flag if main thread use sync 
			request.bNoUseThread = false;
		}

		ImageRequest oldRequest = mRequestMap.get(request.target);
		if (oldRequest != null) {
			oldRequest.setCancel();
			mRequestMap.remove(oldRequest.target);
		}
		
		mRequestMap.put(request.target, request);
		if (request.options != null) {
			request.bindBitmap(request.options.loadingImage);
			request.callOnImageLoadingStart();
		}
		if (isSameRequest(request)) {
			return;
		}
		enqueue(request);
	}

	private boolean isMainThread() {
		return (Thread.currentThread() == Looper.getMainLooper().getThread());
	}

	private synchronized boolean isSameRequest(ImageRequest request) {
		for (ImageRequest wr : mWaitingQueue) {
			if (wr.isSameRequest(request)) {
				return true;
			}
		}
		for (ImageRequest rr : mRunningQueue) {
			if (rr.isSameRequest(request)) {
				return true;
			}
		}
		return false;
	}

	public synchronized void enqueue(ImageRequest request) {
		mWaitingQueue.add(request);
		notify();
	}

	public synchronized ImageRequest dequeue() {
		while (mWaitingQueue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			ImageRequest request = mWaitingQueue.remove(0);
			if (request != null) {
				mRunningQueue.add(request);
			}
			return request;
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized void removeRequest(ImageRequest request) {
		mWaitingQueue.remove(request);
	}

	public class ImageDownloader implements Runnable {

		boolean isFinish = false;

		@Override
		public void run() {
			while (!isFinish) {
				ImageRequest request = dequeue();
				if (request == null) {
					continue;
				}
				if (request.type == ImageRequest.TYPE_END_LOOP) {
					isFinish = true;
					continue;
				}
				processRequest(request);
			}
		}
	}

	private void setFail(ImageRequest request) {
		request.resultCode = ImageRequest.RESULT_CODE_FAIL;
	}

	public void processRequest(ImageRequest request) {
		try {
			URL url = new URL(request.imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			if (request.isCacnel()) {
				return;
			}
			int responseCode = conn.getResponseCode();
			if (request.isCacnel()) {
				return;
			}
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				request.is = is;
				Bitmap bm = null;
				if (configuration != null) {
					bm = configuration.cacheManager.saveBitmap(
							request.imageUrl, is);
				} else {
					bm = BitmapFactory.decodeStream(is);
				}
				request.resultBitmap = bm;
				request.resultCode = ImageRequest.RESULT_CODE_OK;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			setFail(request);
		} catch (IOException e) {
			e.printStackTrace();
			setFail(request);
		}
		
		if (!request.isCacnel()) {
			mMainHandler.post(request);
		}
	}

	public synchronized void removeRunningQueue(ImageRequest request) {
		mRunningQueue.remove(request);
		mRequestMap.remove(request.target);
		for (ImageRequest sr : request.sameRequest) {
			mRequestMap.remove(sr.target);
		}
	}

}
