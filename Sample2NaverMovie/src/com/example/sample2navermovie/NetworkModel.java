package com.example.sample2navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

public class NetworkModel {

	private static NetworkModel instance;
	private Handler mainHandler;
	
	private HashMap<Context,ArrayList<NetworkRequest>> mRequestMap = new HashMap<Context,ArrayList<NetworkRequest>>();

	private final static int THREAD_COUNT = 5;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}

	private NetworkModel() {
		CookieHandler.setDefault(new CookieManager());
		mainHandler = new Handler(Looper.getMainLooper());
		for (int i = 0 ; i < THREAD_COUNT; i++) {
			ImageDownloadRunnable r = new ImageDownloadRunnable(mainHandler);
			new Thread(r).start();
		}
	}

	public interface OnNetworkResultListener {
		public void onResultSuccess(NetworkRequest request);

		public void onResultFail(NetworkRequest request, int errorCode);
	}

	public void getNetworkData(Context context,NetworkRequest request,
			OnNetworkResultListener listener) {
		addRequestMap(context, request);
		MovieListDownloadTask task = new MovieListDownloadTask();
		task.setOnNetworkResultListener(listener);
		task.execute(request);
	}

	public void getNetworkData(Context context,NetworkRequest request) {
		getNetworkData(context, request, new OnNetworkResultListener() {

			@Override
			public void onResultSuccess(NetworkRequest request) {
				request.sendSuccess();
			}

			@Override
			public void onResultFail(NetworkRequest request, int errorCode) {
				request.sendError(errorCode);
			}

		});
	}

	ArrayList<ImageRequest> mRequestQueue = new ArrayList<ImageRequest>();

	public synchronized void enqueue(ImageRequest request) {
		mRequestQueue.add(request);
		notify();
	}

	public void remove(ImageRequest request) {
		mRequestQueue.remove(request);
	}
	
	public synchronized ImageRequest dequeue() {
		ImageRequest request = null;
		while (mRequestQueue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		request = mRequestQueue.remove(0);
		return request;
	}

	public void getNetworkImage(Context context, ImageRequest request) {
		Bitmap bitmap = CacheManager.getInstance().getCacheBitmap(request.getKey());
		
		if (bitmap != null) {
			request.setResult(bitmap);
			request.sendSuccess();
			return;
		}
		
		addRequestMap(context, request);
		for (ImageRequest r : mRequestQueue) {
			if (r.isSameRequest(request)) {
				return;
			}
		}
		
		for (ImageRequest rr : mRunningQueue) {
			if (rr.isSameRequest(request)) {
				return;
			}
		}
		enqueue(request);
	}

	public void addRequestMap(Context context, NetworkRequest request) {
		ArrayList<NetworkRequest> list = mRequestMap.get(context);
		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		request.context = context;
		list.add(request);
	}
	
	public void removeRequestMap(NetworkRequest request) {
		ArrayList<NetworkRequest> list = mRequestMap.get(request.context);
		if (list != null) {
			list.remove(request);
			if (list.size() == 0) {
				mRequestMap.remove(request.context);
			}
		}
	}
	
	public void cancelRequestMap(Context context) {
		ArrayList<NetworkRequest> list = mRequestMap.get(context);
		for(NetworkRequest request : list) {
			request.cancel(false);
		}
		mRequestMap.remove(context);
	}
	
	ArrayList<ImageRequest> mRunningQueue = new ArrayList<ImageRequest>();
	
	public class ImageDownloadRunnable implements Runnable {
		Handler mMainHandler;
		boolean isRunning = false;
		public ImageDownloadRunnable(Handler handler) {
			this.mMainHandler = handler;
			isRunning = true;
		}

		@Override
		public void run() {
			while (isRunning) {
				final ImageRequest request = dequeue();
				if (request == null) continue;
				mRunningQueue.add(request);
				HttpURLConnection conn = null;
				InputStream is = null;
				try {
					URL url = request.getURL();
					conn = (HttpURLConnection) url.openConnection();
					request.setRequestMethod(conn);
					request.setConnectionConfig(conn);
					request.setTimeout(conn);
					request.setOutput(conn);
					if (request.isCanceled()) continue;
					int responseCode = conn.getResponseCode();
					if (request.isCanceled()) continue;
					request.setConnectionConfig(conn);
					if (responseCode == HttpURLConnection.HTTP_OK) {
						is = conn.getInputStream();
						request.process(is);
					}
					mMainHandler.post(new Runnable() {

						@Override
						public void run() {
							request.sendSuccess();
						}
					});
					continue;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (conn != null) {
						conn.disconnect();
					}
					removeRequestMap(request);
					mRunningQueue.remove(request);
				}
				mMainHandler.post(new Runnable() {

					@Override
					public void run() {
						request.sendError(-1);
					}
				});
			}
		}
	}

}
