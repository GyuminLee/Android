package com.example.sample4networkmelon.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.begentgroup.xmlparser.XMLParser;
import com.example.sample4networkmelon.MyApplication;
import com.example.sample4networkmelon.entity.Melon;
import com.example.sample4networkmelon.entity.MelonResult;
import com.example.sample4networkmelon.entity.NaverMovie;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class NetworkManager {

	private static NetworkManager instance;
	AsyncHttpClient client;
	Gson gson;
	XMLParser parser;
	
	Handler mHandler;
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	private NetworkManager() {
		mHandler = new Handler(Looper.getMainLooper());
		client = new AsyncHttpClient();
		client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
		client.setTimeout(30000);
		gson = new Gson();
		parser = new XMLParser();
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}
	
	public static final String MELON_URL = "http://apis.skplanetx.com/melon/charts/realtime";
	public static final String VALUE_ACCEPT = "application/json";
	public static final String VALUE_APPKEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	
	public void getMelonData(Context context, int count, int page, final OnResultListener<Melon> listener) {
		RequestParams params = new RequestParams();
		params.put("count", ""+count);
		params.put("page", ""+page);
		params.put("version", "1");
		
		Header headers[] = new Header[2];
		headers[0] = new BasicHeader("Accept", VALUE_ACCEPT);
		headers[1] = new BasicHeader("appKey", VALUE_APPKEY);
		
		client.get(context, MELON_URL, headers, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				MelonResult mr = gson.fromJson(responseString, MelonResult.class);
				listener.onSuccess(mr.melon);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public static final String API_KEY = "55f1e342c5bce1cac340ebb6032c7d9a";
	public static final String TARGET = "movie";
	public static final String MOVIE_URL = "http://openapi.naver.com/search";
	public void getNaverMovieData(Context context, String keyword,int display, int start, final OnResultListener<NaverMovie> listener) {
		RequestParams params = new RequestParams();
		params.put("query", keyword);
		params.put("key", API_KEY);
		params.put("target", TARGET);
		params.put("display", ""+display);
		params.put("start", ""+start);
		client.get(context, MOVIE_URL, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				ByteArrayInputStream bais = new ByteArrayInputStream(responseBody);
				NaverMovie movie = parser.fromXml(bais, "channel", NaverMovie.class);
				listener.onSuccess(movie);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
		
	}
	
	public void uploadFile(Context context, String path, final OnResultListener<String> listener) {
		RequestParams params = new RequestParams();
		try {
			params.put("myfile", new File(path));
			client.post(context, "xxxx", params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					listener.onSuccess(responseString);
				}
				
				@Override
				public void onProgress(int bytesWritten, int totalSize) {
					super.onProgress(bytesWritten, totalSize);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode);
				}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getNaverMovie(String keyword, OnResultListener<NaverMovie> listener) {
		new Thread(new MovieRunnable(keyword, listener)).start();
	}
	
	
	class MovieRunnable implements Runnable {
		String keyword;
		OnResultListener<NaverMovie> mListener;
		
		public MovieRunnable(String keyword, OnResultListener<NaverMovie> listener) {
			this.keyword = keyword;
			mListener = listener;
		}
		
		@Override
		public void run() {
			try {
				String encodedKeyword = URLEncoder.encode(keyword, "utf8");
				String urlString = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=movie&query=" + encodedKeyword;
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					XMLParser parser = new XMLParser();
					final NaverMovie movie = parser.fromXml(is, "channel", NaverMovie.class);
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							if (mListener != null) {
								mListener.onSuccess(movie);
							}
						}
					});
					return;
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
						mListener.onFail(0);
					}
				}
			});
		}
	}
	
	public void getMelon(String url, OnResultListener<Melon> listener) {
		new MelonTask(listener).execute(url);
	}
	
	static class MelonTask extends AsyncTask<String, Integer, Melon> {
		
		OnResultListener<Melon> mListener;
		
		public MelonTask(OnResultListener<Melon> listener) {
			super();
			mListener = listener;
		}
		
		@Override
		protected Melon doInBackground(String... params) {
			String urlString = params[0];
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					Gson gson = new Gson();
					MelonResult mr = gson.fromJson(new InputStreamReader(is), MelonResult.class);
					return mr.melon;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Melon result) {
			super.onPostExecute(result);
			if (mListener != null) {
				if (result != null) {
					mListener.onSuccess(result);
				} else {
					mListener.onFail(0);
				}
			}
		}
	}
}
