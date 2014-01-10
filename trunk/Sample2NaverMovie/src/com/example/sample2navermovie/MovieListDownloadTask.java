package com.example.sample2navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

import android.os.AsyncTask;

public class MovieListDownloadTask extends
		AsyncTask<MovieRequest, Integer, MovieRequest> {

	NetworkModel.OnNetworkResultListener mListener;
	public void setOnNetworkResultListener(NetworkModel.OnNetworkResultListener listener) {
		mListener = listener;
	}
	
	@Override
	protected MovieRequest doInBackground(MovieRequest... params) {
		HttpURLConnection conn = null;
		InputStream is = null;
		MovieRequest request = params[0];
		try {
			URL url = request.getURL();
			conn = (HttpURLConnection) url.openConnection();

			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				request.process(is);
				return request;
			}
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
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(MovieRequest result) {
		if (result != null) {
			if (mListener != null) {
				mListener.onResultSuccess(result);
			}
		} else {
			if (mListener != null) {
				mListener.onResultFail(-1);
			}
		}
	}

}
