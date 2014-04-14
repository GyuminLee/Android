package com.example.sample3melonchart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class NetworkModel {

	AsyncHttpClient client;
	
	private static NetworkModel instance;
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	private NetworkModel() {
		client = new AsyncHttpClient();
		client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
		client.setTimeout(30000);
	}
	
	public interface OnNetworkResultListener<T> {
		public void onResult(T result);
	}
	
	public void cancelRequests(Context context) {
		client.cancelRequests(context, false);
	}
	
	public void getMelon(Context context, int count, int page, final OnNetworkResultListener<Melon> listener) {
		String url = "http://apis.skplanetx.com/melon/charts/realtime";
		RequestParams params = new RequestParams();
		params.put("count", "" + count);
		params.put("page", "" + page);
		params.put("version", "1");
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
		headers[1] = new BasicHeader("Accept","application/json");
		client.get(context, url, headers, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String content = new String(responseBody);
				Gson gson = new Gson();
				MelonResult mr = gson.fromJson(content, MelonResult.class);
				listener.onResult(mr.melon);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				
			}
		});
//		new MelonTask(listener).execute((Integer)count,(Integer)page);
	}
	
	public void getMelonPost(Context context, int count, int page, final OnNetworkResultListener<Melon> listener) {
		String url = "http://apis.skplanetx.com/melon/charts/realtime?count="+count+"&page="+page+"&version=1";
		RequestParams params = new RequestParams();
		params.put("count", ""+ count);
		params.put("page","" + page);
		client.post(context, url, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void getMelonJson(Context context, int count, int page, final OnNetworkResultListener<Melon> listener) {
		String url = "";
		Gson gson = new Gson();
		MyObject object = new MyObject();
		object.count = count;
		object.page = page;
		String json = gson.toJson(object);
		StringEntity entity = null;
		try {
			entity = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.post(context, url, entity, "application/json", new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void getMelonFile(Context context, String filepath, final OnNetworkResultListener<Melon> listener) {
		String url = "";
		RequestParams params = new RequestParams();
		try {
			params.put("profileImage", new File(filepath) , new File(filepath + "1"));
			params.put("profileImage", new File(filepath+"2"));
			client.post(context, url, params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					
				}
				
				@Override
				public void onProgress(int bytesWritten, int totalSize) {
					super.onProgress(bytesWritten, totalSize);
				}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class MelonTask extends AsyncTask<Integer, Integer, Melon> {
		OnNetworkResultListener<Melon> mListener;
		public MelonTask(OnNetworkResultListener<Melon> listener) {
			super();
			mListener = listener;
		}
		
		@Override
		protected Melon doInBackground(Integer... params) {
			int count = params[0];
			int page = params[1];
			String urlString = "http://apis.skplanetx.com/melon/charts/realtime?count="+count+"&page="+page+"&version=1";
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				conn.setRequestProperty("Accept", "application/json");
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					Gson gson = new Gson();
					MelonResult mr = gson.fromJson(isr, MelonResult.class);
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
			if (result != null) {
				mListener.onResult(result);
			}
		}
	}
}
