package org.tacademy.pjt.daum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DaumOAuthProjectActivity extends Activity {
    /** Called when the activity is first created. */

	static final String REQUEST_TOKEN_URL = "https://apis.daum.net/oauth/requestToken"; 
	static final String AUTHORIZE_URL = "https://apis.daum.net/oauth/authorize";
	static final String ACCESS_TOKEN_URL = "https://apis.daum.net/oauth/accessToken";
	
	static final String CONSUMER_KEY = "a354b395-64cb-4457-82c0-1fcc4dc977d5";
	static final String CONSUMER_SECRET = "EaJNNmjpIZxHltQ-j1y6u6h3FxsKcwOATd47vfpYwGR.Noh8GWiviw00";
	
	// API prefix 
	static final String API_URL = "https://apis.daum.net";

	static OAuthProvider provider = new DefaultOAuthProvider(REQUEST_TOKEN_URL, ACCESS_TOKEN_URL, AUTHORIZE_URL);	
	static OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

	static final String PREF_NAME = "token";
	
	Button loginButton;
	Button dataButton;
	TextView text;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		SharedPreferences pref = getSharedPreferences(PREF_NAME,0);
		final String token = pref.getString("token", null);
		final String secret = pref.getString("secret", null);
		final String verifier = pref.getString("verifier",null);

		text = (TextView)findViewById(R.id.textView1);
		
		loginButton = (Button)findViewById(R.id.button1);
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				new Thread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						try {
							final String authUrl = provider.retrieveRequestToken(consumer, "http://dongjaguestbook.appspot.com/guestbook");
							runOnUiThread(new Runnable() {

								public void run() {
									// TODO Auto-generated method stub
									Intent i = new Intent(DaumOAuthProjectActivity.this,InAppBrowser.class);
									i.putExtra("url", authUrl);
									startActivityForResult(i,0);									
								}
								
							});
						} catch (OAuthMessageSignerException e) {
							e.printStackTrace();
						} catch (OAuthNotAuthorizedException e) {
							e.printStackTrace();
						} catch (OAuthExpectationFailedException e) {
							e.printStackTrace();
						} catch (OAuthCommunicationException e) {
							e.printStackTrace();
						}
					}
					
				}).start();
			}
		});		
		dataButton = (Button)findViewById(R.id.button2);
		dataButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				new Thread(new Runnable() {
					public void run() {
						try {
							URL url = new URL(API_URL + "/cafe/favorite_cafes.json");		
							HttpURLConnection request = (HttpURLConnection) url.openConnection();
							consumer.sign(request);		
							
							request.connect();
							
							final StringBuilder sb = new StringBuilder();
							BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
							String tmpStr = "";
							while( (tmpStr = br.readLine()) != null) {
								sb.append(tmpStr);
							}
							runOnUiThread(new Runnable() {
								
								public void run() {
									// TODO Auto-generated method stub
									text.setText(sb.toString());
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}						
					}
				}).start();
			}
		});
		if (token != null && secret != null && verifier != null) {
			new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					try {
						consumer.setTokenWithSecret(token, secret);
//						provider.retrieveAccessToken(consumer, verifier);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
			});
			loginButton.setEnabled(false);
			dataButton.setEnabled(true);
		} else {
			loginButton.setEnabled(true);
			dataButton.setEnabled(false);
		}
		

		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				// intent로 부터 AuthCode를 가져와서 처리함.
				final String verifier = data.getStringExtra("verifier");
				new Thread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						try {
							provider.retrieveAccessToken(consumer, verifier);
							runOnUiThread(new Runnable() {
								
								public void run() {
									// TODO Auto-generated method stub
									saveAccessToken(consumer,verifier);
									loginButton.setEnabled(false);
									dataButton.setEnabled(true);									
								}
							});
						} catch (OAuthMessageSignerException e) {
							e.printStackTrace();
						} catch (OAuthNotAuthorizedException e) {
							e.printStackTrace();
						} catch (OAuthExpectationFailedException e) {
							e.printStackTrace();
						} catch (OAuthCommunicationException e) {
							e.printStackTrace();
						}						
					}
					
				}).start();
			}
		}
	}
	
	private void saveAccessToken(OAuthConsumer consumer,String verifier) {
		String token = consumer.getToken();
		String secret = consumer.getTokenSecret();
		SharedPreferences pref = this.getSharedPreferences(PREF_NAME,0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("token", token);
		editor.putString("secret", secret);
		editor.putString("verifier", verifier);
		editor.commit();
		
	}
	
}