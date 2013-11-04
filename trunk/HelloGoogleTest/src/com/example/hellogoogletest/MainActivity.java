package com.example.hellogoogletest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView messageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.textView1);
		Button btn = (Button)findViewById(R.id.btnGoogle);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new MyTask().execute("http://www.google.com");				
			}
		});
	}
	
	class MyTask extends AsyncTask<String,Integer,String> {
		@Override
		protected String doInBackground(String... params) {
			String urlText = params[0];
			try {
				URL url = new URL(urlText);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line;
					while((line = br.readLine()) != null) {
						sb.append(line);
						sb.append("\n\r");
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
			if (result != null) {
				messageView.setText(result);
			} else {
				Toast.makeText(MainActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
