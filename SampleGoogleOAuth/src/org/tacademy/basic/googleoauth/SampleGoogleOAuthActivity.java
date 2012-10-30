package org.tacademy.basic.googleoauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SampleGoogleOAuthActivity extends Activity {
    /** Called when the activity is first created. */
	public static final String RESPONSE_TYPE="code";
	public static final String SCOPE="https://mail.google.com/mail/feed/atom";
	public static final String REDIRECT_URI="http://localhost";
	public static final String CLIENT_ID="972646024433.apps.googleusercontent.com";
	public static final String CLIENT_SECRET="By5eGC_7t7TkSd-tipfP3KdN";
	public static final String STATE="mail";
	// 1. SCOPE
	// 2. STATE
	// 3. REDIRECT_URI
	// 4. RESPONSE_TYPE
	// 5. CLIENT_ID
	public static final String AUTHENTICATION_ENDPOINT_URL = "https://accounts.google.com/o/oauth2/auth?scope=%s&state=%s&redirect_uri=%s&response_type=%s&client_id=%s";
	
	public String mAuthenticationEndPoint;

	public static final String FIELD_NAME_CODE="code";
	public static final String FIELD_NAME_STATE="state";
	
	public static final String USER_AGENT="MyAndroidGoogleApp";
	public static final String GRANT_TYPE="authorization_code";
	
	Handler mHandler = new Handler();
	
	TextView textView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView)findViewById(R.id.text);
        try {
			mAuthenticationEndPoint = String.format(AUTHENTICATION_ENDPOINT_URL, 
					URLEncoder.encode(SCOPE, "UTF-8"),
					STATE,
					URLEncoder.encode(REDIRECT_URI,"UTF-8"),
					RESPONSE_TYPE,
					CLIENT_ID);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.login :
			Intent intent = new Intent(this, WebViewActivity.class);
			intent.setData(Uri.parse(mAuthenticationEndPoint));
			startActivityForResult(intent,0);
			return true;
		case R.id.exit :
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 0:
			if (resultCode != Activity.RESULT_OK || data == null) {
				return;
			}
			final String code = data.getStringExtra(FIELD_NAME_CODE);
			final String state = data.getStringExtra(FIELD_NAME_STATE);
			if (code != null) {
				Thread th = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						getAccessTokenFromGoogle(code);
					}
					
				});
				th.start();
			} else {
				Toast.makeText(this, "코드 획득 실패", Toast.LENGTH_SHORT).show();
			}
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    
	public void getAccessTokenFromGoogle(String code) {
		try {
			URL url = new URL("https://accounts.google.com/o/oauth2/token");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("user-agent", USER_AGENT);
			OutputStream os = conn.getOutputStream();
			StringBuilder sb = new StringBuilder();
			sb.append("code").append("=").append(code);
			sb.append("&");
			sb.append("redirect_uri").append("=").append(URLEncoder.encode(REDIRECT_URI, "UTF-8"));
			sb.append("&");
			sb.append("client_id").append("=").append(CLIENT_ID);
			sb.append("&");
			sb.append("scope").append("=").append(SCOPE);
			sb.append("&");
			sb.append("client_secret").append("=").append(CLIENT_SECRET);
			sb.append("&");
			sb.append("grant_type").append("=").append(GRANT_TYPE);
			os.write(sb.toString().getBytes("UTF-8"));
			os.close();
			
			int respCode = conn.getResponseCode();
			if (respCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				parseInputStream(is);
				is.close();
			} else {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(SampleGoogleOAuthActivity.this, "post 접속 실패", Toast.LENGTH_SHORT).show();
					}
					
				});
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void parseInputStream(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String str = null;
		while((str = br.readLine()) != null) {
			sb.append(str).append("\n\r");
		}
		
		try {
			JSONObject jObject = new JSONObject(sb.toString());
			final String access_token = jObject.getString("access_token");
			final String token_type = jObject.getString("token_type");
			final String expires_in = jObject.getString("expires_in");
			final String refresh_token = jObject.getString("refresh_token");
			
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					getGmailAtomFeed(access_token);					
				}
				
			});
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getGmailAtomFeed(final String access_token) {
		// TODO Auto-generated method stub
		Thread th = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url = new URL("https://mail.google.com/mail/feed/atom");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.addRequestProperty("Authorization", "OAuth "+access_token);
					int respCode = conn.getResponseCode();
					if (respCode == HttpURLConnection.HTTP_OK) {
						StringBuilder sb = new StringBuilder();
						InputStream is = conn.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String str;
						while((str = br.readLine()) != null) {
							sb.append(str).append("\n\r");
						}
						final String text = sb.toString();
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								textView.setText(text);
							}
							
						});
					} else {
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(SampleGoogleOAuthActivity.this, "데이터 수신 실패", Toast.LENGTH_SHORT).show();
							}
							
						});
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		th.start();
	}
	
    
}