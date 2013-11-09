package com.example.hellonaveropenapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

public class NetworkModel {
	private static NetworkModel instance;
	HashMap<Context, ArrayList<NetworkRequest>> mRequestMap = new HashMap<Context, ArrayList<NetworkRequest>>();
	public static final int MAX_THREAD_COUNT = 5;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}

	private NetworkModel() {
		for (int i = 0; i < MAX_THREAD_COUNT; i++) {
			new MyImageDownloadTask().execute("");
		}
	}

	public interface OnResultListener {
		public void onSuccess(Object result);

		public void onError(int errorCode);
	}

	public void getNetworkData(Context context, NetworkRequest request) {
		request.context = context;
		ArrayList<NetworkRequest>  list = mRequestMap.get(context);
		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		list.add(request);
		new MyDownloadRequest().execute(request);
	}

	HashMap<String,WeakReference<Bitmap>> mMemCache = new HashMap<String,WeakReference<Bitmap>>();

	public void setCache(String key, Bitmap bitmap) {
		WeakReference<Bitmap> ref = new WeakReference<Bitmap>(bitmap);
		mMemCache.put(key, ref);		
	}
	public InputStream saveFile(String key, InputStream is) throws IOException {
		File file = new File(Environment.getExternalStorageDirectory(), key);
		FileOutputStream out = new FileOutputStream(file);
		byte[] buffer = new byte[4096];
		int len;
		while((len = is.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		out.close();
		is.close();
		FileInputStream in = new FileInputStream(file);
		return in;
	}
	
	public Bitmap getFileCache(String key) throws IOException {
		File file = new File(Environment.getExternalStorageDirectory(),key);
		if (!file.exists()) return null;
		FileInputStream fis = new FileInputStream(file);
		Bitmap bm = BitmapFactory.decodeStream(fis);
		fis.close();
		return bm;
	}
	
	public Bitmap getMemeCache(String key) {
		WeakReference<Bitmap> ref = mMemCache.get(key);
		if (ref == null) return null;
		Bitmap bm = ref.get();
		if (bm != null) return bm;
		mMemCache.remove(key);
		return null;
	}
	
	public Bitmap getCache(ImageRequest request) {
		Bitmap bm = getMemeCache(request.getKey());
		if (bm != null) return bm;
		try {
			bm = getFileCache(request.getKey());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bm == null) return null;
		setCache(request.getKey(), bm);
		return bm;
	}
	
	public void getImageData(Context context, ImageRequest request) {
		Bitmap bm = getCache(request);
		if (bm != null) {
			request.setResult(bm);
			request.sendResult();
			return ;
		}
		
		request.context = context;
		ArrayList<NetworkRequest>  list = mRequestMap.get(context);
		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		list.add(request);
		for (ImageRequest req : mQueue) {
			if (req.isSameRequest(request)) {
				return;
			}
		}
		for (ImageRequest req : mRunningQueue) {
			if (req.isSameRequest(request)) {
				return;
			}
		}
		enqueue(request);
	}
	
	public void removeImageRequest(ImageRequest request) {
		mQueue.remove(request);
	}
	
	public void cleanUpRequest(Context context) {
		ArrayList<NetworkRequest> list = mRequestMap.get(context);
		if (list != null) {
			for (NetworkRequest request : list) {
				request.setCancel();
			}
		}
	}

	ArrayList<ImageRequest> mQueue = new ArrayList<ImageRequest>();
	ArrayList<ImageRequest> mRunningQueue = new ArrayList<ImageRequest>();
	
	public synchronized void enqueue(ImageRequest request) {
		mQueue.add(request);
		notify();
	}
	
	public synchronized ImageRequest dequeue() {
		while (mQueue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ImageRequest request = mQueue.remove(0);
		if (request != null) {
			mRunningQueue.add(request);
		}
		return request;
	}
	

	class MyImageDownloadTask extends AsyncTask<String,ImageRequest,Boolean> {
		boolean isRunning = true;
		@Override
		protected Boolean doInBackground(String... params) {
			ImageRequest request = null;
			while(isRunning) {
				request = dequeue();
				if (request == null) continue;
				URL url = request.getURL();
				int retry = 3;
				while (retry > 0) {
					try {
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod(request.getRequestMethod());
						conn.setConnectTimeout(request.getConnectionTimeout());
						conn.setReadTimeout(request.getReadTimeout());
						request.setRequestHeader(conn);
						if (request.isCancel()) {
							retry = 0;
							continue;
						}
						request.setOutput(conn);
						if (request.isCancel()) {
							retry = 0;
							continue;
						}
						int resCode = conn.getResponseCode();
						if (request.isCancel()) {
							retry = 0;
							continue;
						}
						if (resCode == HttpURLConnection.HTTP_OK) {
							InputStream is = conn.getInputStream();
							request.process(is);
							publishProgress(request);
							retry = 0;
							continue;
						} else {
							retry = 0;
							continue;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					retry--;
				}
				
			}
			return null;
		}
		
		@Override
		protected void onCancelled() {
			isRunning = false;
			super.onCancelled();
		}
		
		@Override
		protected void onProgressUpdate(ImageRequest... values) {
			ImageRequest request = values[0];
			if (request != null) {
				request.sendResult();
				ArrayList<NetworkRequest> list = mRequestMap.get(request.context);
				list.remove(request);
			}
			mRunningQueue.remove(request);
			super.onProgressUpdate(values);
		}
	}
	
	
	
	
	
	
	class MyDownloadRequest extends
			AsyncTask<NetworkRequest, Integer, Boolean> {
		NetworkRequest mRequest;

		@Override
		protected Boolean doInBackground(NetworkRequest... params) {
			NetworkRequest request = params[0];
			mRequest = request;
			URL url = request.getURL();
			int retry = 3;
			while (retry > 0) {
				try {
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod(request.getRequestMethod());
					conn.setConnectTimeout(request.getConnectionTimeout());
					conn.setReadTimeout(request.getReadTimeout());
					request.setRequestHeader(conn);
					if (request.isCancel()) {
						return false;
					}
					request.setOutput(conn);
					if (request.isCancel()) {
						return false;
					}
					int resCode = conn.getResponseCode();
					if (request.isCancel()) {
						return false;
					}
					if (resCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						request.process(is);
						return true;
					} else {
						return false;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				retry--;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				mRequest.sendResult();
			} else {
				mRequest.sendError(0);
			}
			ArrayList<NetworkRequest> list = mRequestMap.get(mRequest.context);
			list.remove(mRequest);
			super.onPostExecute(result);
		}
	}

}
