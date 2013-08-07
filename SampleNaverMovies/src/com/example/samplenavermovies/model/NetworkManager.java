package com.example.samplenavermovies.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.example.samplenavermovies.parser.InputStreamParserException;

public class NetworkManager {

	private static NetworkManager instance;
	private Handler mainHandler;
	private ImageRequestManager irm;
	private ImageDownloader[] mImageDownloader;
	private HashMap<Context,ArrayList<NetworkRequest>> mRequestMap = new HashMap<Context,ArrayList<NetworkRequest>>();
	
	
	public static final int DOWNLOADER_COUNT = 5;
	
	public interface OnNetworkResultListener {
		public void onSuccess(String keyword, NaverMovieList list);
		public void onFail(String keyword, String error);
	}
	
	public abstract static class OnSimpleNetworkResultListener implements OnNetworkResultListener {

		@Override
		abstract public void onSuccess(String keyword, NaverMovieList list);

		@Override
		public void onFail(String keyword, String error) {			
		}
		
	}
	
	private String userid;
	private String password;
	
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	private NetworkManager() {
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				if (userid != null && !userid.equals("") && 
						password != null && !password.equals("")) {
					return new PasswordAuthentication(userid, password.toCharArray());
				} else {
					
				}
				return null;
			}
		});
		
		mainHandler = new Handler(Looper.getMainLooper());
		irm = new ImageRequestManager();
		mImageDownloader = new ImageDownloader[DOWNLOADER_COUNT];
		for (int i = 0; i < DOWNLOADER_COUNT; i++) {
			mImageDownloader[i] = new ImageDownloader(irm);
			new Thread(mImageDownloader[i]).start();
		}
		
	}
	
	public boolean removeImageRequest(ImageRequest request) {
		irm.remove(request);
		return true;
	}
	
	public boolean getNetworkData(Context context, NetworkRequest request,
			NetworkRequest.OnCompletedListener listener,
			Handler handler) {
		ArrayList<NetworkRequest> list = mRequestMap.get(context);
		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		list.add(request);
		request.setOnCompletedListener(listener);
		request.setHandler(handler);
		
		DataDownloader downloader = new DataDownloader(context, request);
		if (handler != null) {
			new Thread(downloader).start();
		} else if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
			new Thread(downloader).start();
		}else {
			downloader.run();
		}
		
		return true;
	}
	
	
	public void cancelAll(Context context) {
		ArrayList<NetworkRequest> list = mRequestMap.get(context);
		for (int i = 0; i < list.size(); i++) {
			NetworkRequest request = list.get(i);
			request.cancel();
		}
		mRequestMap.remove(context);
	}
	
	public void removeRequest(Context context, NetworkRequest request) {
		ArrayList<NetworkRequest> list = mRequestMap.get(context);
		list.remove(request);
		if (list.size() == 0) {
			mRequestMap.remove(context);
		}
	}
	
	public boolean getImageData(ImageRequest request,
			NetworkRequest.OnCompletedListener listener) {
		// cache...
		Bitmap bm = CacheManager.getInstance().getFileCache(request.getKey());
		if (bm != null) {
			request.sendResult(bm);
			return true;
		}
		request.setOnCompletedListener(listener);
		request.setHandler(mainHandler);
		
		irm.enqueue(request);
		
//		DataDownloader downloader = new DataDownloader(request);
//		new Thread(downloader).start();
		
		return true;
	}
	
	public boolean getNaverMovieList(final String keyword,final  int start,final  int display,
			final OnNetworkResultListener listener, final Handler handler) {

		if (handler != null ) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					process(keyword, start, display, listener,handler);
				}
	
			}).start();
		} else {
			process(keyword, start, display, listener,handler);
		}
		return true;
	}
		
	private void process(final String keyword,final  int start,final  int display,
			final OnNetworkResultListener listener, final Handler handler) {
		String keywordEncoded;
		try {
			keywordEncoded = URLEncoder.encode(keyword, "utf-8");
			String urlString = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&query="+keywordEncoded+"&display="+display+"&start="+start+"&target=movie";
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				final NaverMovieList list = parsing(is);
				if (handler != null) {
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							listener.onSuccess(keyword, list);
						}
					});
				} else {
					listener.onSuccess(keyword, list);
				}
			} else {
				listener.onFail(keyword, "....");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			if (handler != null) {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						listener.onFail(keyword, "...");
					}
				});
			} else {
				listener.onFail(keyword, "...");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			if (handler != null) {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						listener.onFail(keyword, "...");
					}
				});
			} else {
				listener.onFail(keyword, "...");
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (handler != null) {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						listener.onFail(keyword, "...");
					}
				});
			} else {
				listener.onFail(keyword, "...");
			}
		}
		
	}
	
	private NaverMovieList parsing(InputStream is) {
		NaverMovieParser parser = new NaverMovieParser();
		try {
			parser.doParse(is);
			return parser.getResult();
		} catch (InputStreamParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
