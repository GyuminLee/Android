package com.example.hellotemptest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	ImageView iconView;
	TextView dateView;
	TextView messageView;
	WeatherTime mData;

	public ItemView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView) findViewById(R.id.weatherIcon);
		dateView = (TextView) findViewById(R.id.weatherDate);
		messageView = (TextView) findViewById(R.id.message);
	}

	MyImageTask mTask;
	
	public void setData(WeatherTime data) {
		mData = data;
		
		if (mTask != null) {
			mTask.cancel(true);
			mTask = null;
		}
		
		iconView.setImageResource(R.drawable.ic_launcher);
		
		if (data.symbol.var != null && !data.symbol.var.equals("")) {
			String imageURL = "http://openweathermap.org/img/w/" + data.symbol.var + ".png";
			mTask = new MyImageTask();
			mTask.execute(imageURL);
		}
		
		dateView.setText(data.day);
		messageView.setText("day : " + data.temperature.day + ",max : "
				+ data.temperature.max + ", min : " + data.temperature.min);
		
	}
	
	class MyImageTask extends AsyncTask<String,Integer,Bitmap> {
		@Override
		protected Bitmap doInBackground(String... params) {
			String imageUrl = params[0];
			try {
				URL url = new URL(imageUrl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					Bitmap bm = BitmapFactory.decodeStream(is);
					return bm;
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
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if (mTask == this && result != null) {
				iconView.setImageBitmap(result);
				mTask = null;
			}
			super.onPostExecute(result);
		}
	}
}
