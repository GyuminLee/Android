package com.example.samplenavermovies;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.samplenavermovies.model.GoogleAccessToken;
import com.example.samplenavermovies.model.GoogleAccessTokenRequest;
import com.example.samplenavermovies.model.GoogleAtomFeedRequest;
import com.example.samplenavermovies.model.NetworkManager;
import com.example.samplenavermovies.model.NetworkRequest;
import com.example.samplenavermovies.model.NetworkRequest.OnCompletedListener;

public class GoogleAtomFeedActivity extends ParentActivity {

	Handler mHandler = new Handler();
	
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
	
	TextView messageView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.atom_feed);
	    messageView = (TextView)findViewById(R.id.message);
	    Button btn = (Button)findViewById(R.id.btnLogin);
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

	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GoogleAtomFeedActivity.this, InAppBrowser.class);
				intent.putExtra(InAppBrowser.PARAM_URL, mAuthenticationEndPoint);
				intent.putExtra(InAppBrowser.PARAM_CALLBACK_URL, REDIRECT_URI);
				intent.putExtra(InAppBrowser.PARAM_CODE_STRING, FIELD_NAME_CODE);
				startActivityForResult(intent, 0);
			}
		});
	    
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String code = data.getStringExtra(InAppBrowser.RETURN_CODE);
				GoogleAccessTokenRequest request = new GoogleAccessTokenRequest(code, REDIRECT_URI, CLIENT_ID, CLIENT_SECRET, SCOPE);
				NetworkManager.getInstance().getNetworkData(GoogleAtomFeedActivity.this, request, new OnCompletedListener() {
					
					@Override
					public void onSuccess(NetworkRequest request, Object result) {
						if (result != null) {
							GoogleAccessToken token = (GoogleAccessToken)result;
							GoogleAtomFeedRequest atom = new GoogleAtomFeedRequest(token);
							NetworkManager.getInstance().getNetworkData(GoogleAtomFeedActivity.this, atom, new OnCompletedListener() {
								
								@Override
								public void onSuccess(NetworkRequest request, Object result) {
									if (result != null) {
										String text = (String)result;
										messageView.setText(text);
									}
								}
								
								@Override
								public void onFail(NetworkRequest request, int errorCode, String errorMsg) {
									// TODO Auto-generated method stub
									
								}
							}, mHandler);
						}
						
					}
					
					@Override
					public void onFail(NetworkRequest request, int errorCode, String errorMsg) {
						// TODO Auto-generated method stub
						
					}
				}, mHandler);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
