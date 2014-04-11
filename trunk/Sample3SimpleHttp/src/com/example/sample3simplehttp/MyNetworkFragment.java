package com.example.sample3simplehttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MyNetworkFragment extends DialogFragment {

	public interface OnResultListener {
		public void onResult(String result);
	}
	
	OnResultListener mListener;
	public void setOnResultListener(OnResultListener listener) {
		mListener = listener;
	}
	
	public static final String PARAM_URL = "url";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		String url = args.getString(PARAM_URL);
		new MyDownloadTask().execute(url);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setTitle("downloading...");
		dialog.setMessage("plz wait...");
		return dialog;
	}
	
	class MyDownloadTask extends AsyncTask<String, Integer, String>{
		@Override
		protected String doInBackground(String... params) {
			String urlString = params[0];
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// conn.addRequestProperty("accept-language", "ko_KR");
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String line;
					StringBuilder sb = new StringBuilder();
					while ((line = br.readLine()) != null) {
						sb.append(line);
						sb.append("\n\r");
					}
					return sb.toString();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			dismiss();
			super.onPostExecute(result);
			if (result != null) {
				if (mListener != null) {
					mListener.onResult(result);
				}
			}
		}
	}
}
