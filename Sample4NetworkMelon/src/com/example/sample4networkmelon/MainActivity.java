package com.example.sample4networkmelon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView messageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.message_view);
		Button btn = (Button)findViewById(R.id.btn_melon);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new MelonTask().execute("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
			}
		});
	}
	
	class MelonTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
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
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					StringBuilder sb = new StringBuilder();
					String line;
					while((line=br.readLine()) != null) {
						sb.append(line + "\n\r");
					}
					return sb.toString();
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
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				messageView.setText(result);
			}
		}
	}
}
