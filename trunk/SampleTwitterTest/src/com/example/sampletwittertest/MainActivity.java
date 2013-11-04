package com.example.sampletwittertest;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String CONSUMER_KEY="9S2O3pBSxGSdHg9A67EEaw";
	public static final String CONSUMER_SECRET="gqSV5N7lTAjH68ShlJkeQ7YU4y4zRWcMlWfuHKhOhgM";
	public static final String CALLBACK_URL="http://www.beintelligentgroup.com/twitter.html";
	
	static final int REQUEST_CODE_BROWSER = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AuthorizeTask().execute("");
			}
		});
	}

	Twitter twitter;
	RequestToken requestToken;
	class AuthorizeTask extends AsyncTask<String,Integer,String> {
		@Override
		protected String doInBackground(String... params) {
			ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
			configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY);
			configurationBuilder.setOAuthConsumerSecret(CONSUMER_SECRET);
			Configuration configuration = configurationBuilder.build();
			TwitterFactory twitterFactory = new TwitterFactory(configuration);
			twitter = twitterFactory.getInstance();
			try {
				requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
				String url = requestToken.getAuthenticationURL();
				return url;
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
//				Toast.makeText(MainActivity.this, "url : " + result, Toast.LENGTH_SHORT).show();
				Intent i = new Intent(MainActivity.this,InAppBrowserActivity.class);
				i.putExtra(InAppBrowserActivity.PARAM_URL, result);
				startActivityForResult(i, REQUEST_CODE_BROWSER);
			}
			super.onPostExecute(result);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_BROWSER && resultCode == RESULT_OK) {
			String verifier = data.getStringExtra(InAppBrowserActivity.RETURN_VERIFIER);
			new AccessTokenTask().execute(verifier);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class AccessTokenTask extends AsyncTask<String,Integer,AccessToken> {
		@Override
		protected AccessToken doInBackground(String... params) {
			String verifier = params[0];
			try {
				AccessToken token = twitter.getOAuthAccessToken(requestToken, verifier);
				twitter.updateStatus("twitter test...");
				return token;
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(AccessToken result) {
			Toast.makeText(MainActivity.this, "token : " + result.getToken() + ", secret : " + result.getTokenSecret(), Toast.LENGTH_SHORT).show();
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
