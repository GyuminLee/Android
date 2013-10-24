package com.example.sampleskpoauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	String url = "https://oneid.skplanetx.com/oauth/authorize?client_id=8934d520-60ae-3d59-a010-18ece73361db&response_type=code&scope=user&redirect_uri=";
	String redirect_url = "http://localhost/";
	String accessTokenUrl = "https://oneid.skplanetx.com/oauth/token?client_id=8934d520-60ae-3d59-a010-18ece73361db&client_secret=52f3aac6-d2eb-3cd4-8cbc-c814c4a51053&scope=user&grant_type=authorization_code";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btnAuth);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, InAppBrowserActivity.class);
				try {
					String encodedUrl = url + URLEncoder.encode(redirect_url, "utf-8");
					i.putExtra("url", encodedUrl);
					startActivityForResult(i, 0);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK) {
			String code = data.getStringExtra("result");
			new AccessTokenTask().execute(code);
		}
	}
	
	class AccessTokenTask extends AsyncTask<String, Integer, AccessToken> {
		@Override
		protected AccessToken doInBackground(String... params) {
			String code = params[0];
			try {
				String encodedUrl = accessTokenUrl + "&redirect_url=" + URLEncoder.encode(redirect_url, "utf-8") +
						"&code=" + code;
				
				URL url = new URL(encodedUrl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line;
					while((line=br.readLine()) != null) {
						sb.append(line);
						sb.append("\n\r");
					}
					
					Gson gson = new Gson();
					AccessToken token = gson.fromJson(sb.toString(), AccessToken.class);
					return token;
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
			return null;
		}
		
		@Override
		protected void onPostExecute(AccessToken result) {
			Toast.makeText(MainActivity.this, "access_token : " + result.access_token, Toast.LENGTH_SHORT).show();
			new ProfileTask().execute(result.access_token);
		}
	}
	
	String profileUrl = "https://apis.skplanetx.com/users/me/profile?version=1";
	
	class ProfileTask extends AsyncTask<String, Integer, Profile> {
		@Override
		protected Profile doInBackground(String... params) {
			String accessToken = params[0];
			String urlString = profileUrl;
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestProperty("Accept","application/json");
				conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				conn.setRequestProperty("access_token", accessToken);
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line;
					while((line = br.readLine()) != null) {
						sb.append(line);
						sb.append("\n\r");
					}
					Gson gson = new Gson();
					ResultProfile rp = gson.fromJson(sb.toString(), ResultProfile.class);
					return rp.profile;
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
		protected void onPostExecute(Profile result) {
			Toast.makeText(MainActivity.this, "UserName : " + result.userName, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
