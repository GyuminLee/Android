package com.example.testwebviewsample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {

	WebView webView;
	final static String CALLBACK_URL = "http://localhost/GETAUTHCODE";
	
	// "http://localhost/GETAUTHCODE?auth_verifier=...&"
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_PROGRESS);
		
		
		setContentView(R.layout.activity_main);
		setProgressBarVisibility(true);
		webView = (WebView)findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				if (url.startsWith("market://")) {
					Uri uri = Uri.parse(url);
					Intent i = new Intent(Intent.ACTION_VIEW,uri);
					startActivity(i);
					return true;
				} else if (url.startsWith(CALLBACK_URL)) {
					//...
					Uri uri = Uri.parse(url);
					String verifier = uri.getQueryParameter("auth_verifier");
					return true;
				}
				return false;
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				setProgress(newProgress * 100); 
			}
			
		});
		
		webView.loadUrl("http://www.google.com");
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (webView.canGoBack()) {
					webView.goBack();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (webView.canGoForward()) {
					webView.goForward();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
