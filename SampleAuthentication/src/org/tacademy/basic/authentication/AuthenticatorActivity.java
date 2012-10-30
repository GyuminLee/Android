package org.tacademy.basic.authentication;

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

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {

	public static final String PARAM_AUTHTOKEN_TYPE = "authTokenType";
	public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";
	public static final String PARAM_REQUIRED_FEATURES = "requiredFeatures";
	public static final String PARAM_OPTIONS = "options";

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
	public static final String GET_ACCESSTOKEN_URL = "https://accounts.google.com/o/oauth2/token";
	public String mAuthenticationEndPoint;

	public static final String FIELD_NAME_CODE="code";
	public static final String FIELD_NAME_STATE="state";
	
	public static final String USER_AGENT="MyAndroidGoogleApp";
	public static final String GRANT_TYPE="authorization_code";
	
	public static final String PREF_NAME = "prefs";
	public static final String FIELD_ACCESS_TOKEN = "accessToken";
	public static final String FIELD_TOKEN_TYPE = "tokenType";
	public static final String FIELD_REFRESH_TOKEN = "refreshToken";
	public static final String FIELD_USER_ID = "userId";
	
	public String mCode;
	public String mAccessToken;
	public String mTokenType;
	public String mRefreshToken;
	
	boolean mCredentials;
	
	Handler mHandler = new Handler();
	AccountManager mAccountManager;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_PROGRESS);
	    setContentView(R.layout.webview_layout);
	    WebView webview = (WebView)findViewById(R.id.webView1);
	    Intent i = getIntent();
	    mCredentials = i.getBooleanExtra(PARAM_CONFIRMCREDENTIALS, false);
	    webview.getSettings().setJavaScriptEnabled(true);
	    
	    mAccountManager = AccountManager.get(this);
	    
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

		if (mAuthenticationEndPoint != null) {
	    	webview.loadUrl(mAuthenticationEndPoint);
		}
	    webview.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int progress) {
				// TODO Auto-generated method stub
				setProgress(progress * 100);
			}
	    	
	    });
	    
	    webview.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				setTitle(url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				if (url.startsWith(REDIRECT_URI)) {
					Uri uri = Uri.parse(url);
					final String code = uri.getQueryParameter("code");
					final String state = uri.getQueryParameter("state");
					if (code != null) {
						mCode = code;
						Thread th = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								getAccessTokenFromGoogle(code);
							}
							
						});
						th.start();
						return true;
					} 
					Toast.makeText(AuthenticatorActivity.this, "코드 획득 실패", Toast.LENGTH_SHORT).show();
					
					failedFinish();
					return true;
				}
				return false;
			}
			
	    });
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		CookieSyncManager.getInstance().stopSync();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CookieSyncManager.getInstance().startSync();
	}

	
	public void failedFinish() {
		if (mCredentials) {
	        final Intent intent = new Intent();
	        intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, false);
	        setAccountAuthenticatorResult(intent.getExtras());
	        setResult(RESULT_OK, intent);
		}
        finish();			
	}
	public void getAccessTokenFromGoogle(String code) {
		try {
			URL url = new URL(GET_ACCESSTOKEN_URL);
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
				return;
			} else {
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(AuthenticatorActivity.this, "post 접속 실패", Toast.LENGTH_SHORT).show();
			}
			
		});
		failedFinish();
	}

	public void parseInputStream(InputStream is) throws IOException , JSONException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String str = null;
		while((str = br.readLine()) != null) {
			sb.append(str).append("\n\r");
		}
		
		JSONObject jObject = new JSONObject(sb.toString());
		final String access_token = jObject.getString("access_token");
		final String token_type = jObject.getString("token_type");
		final String expires_in = jObject.getString("expires_in");
		final String refresh_token = jObject.getString("refresh_token");
		
		mAccessToken = access_token;
		mTokenType = token_type;
		mRefreshToken = refresh_token;
				
		getUserInfo(access_token);
			
	}

	public void getUserInfo(final String access_token) {
		// TODO Auto-generated method stub
		Thread th = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url = new URL("https://www.googleapis.com/oauth2/v1/tokeninfo?access_token="+access_token);
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
						JSONObject jObject = new JSONObject(sb.toString());
						String user_id = jObject.getString("userid");
						
						if (mCredentials) {
					        final Intent intent = new Intent();
					        intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, true);
					        setAccountAuthenticatorResult(intent.getExtras());
					        setResult(RESULT_OK, intent);
							finish();
						} else {
							Account account = new Account(user_id, Constants.ACCOUNT_TYPE);
													
							mAccountManager.addAccountExplicitly(account, mRefreshToken, null);
							
							Intent i = new Intent();
							i.putExtra(AccountManager.KEY_ACCOUNT_NAME, user_id);
							i.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
							i.putExtra(AccountManager.KEY_AUTHTOKEN, mAccessToken);
							setAccountAuthenticatorResult(i.getExtras());
							setResult(RESULT_OK,i);
							finish();
						}
						return;
					} else {
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(AuthenticatorActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
					}
					
				});
				failedFinish();
			}
			
		});
		th.start();
	}
	
}
