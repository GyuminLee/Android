package com.example.sample2webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	WebView webView;
	EditText urlView;
	TextView messageView;
	private static final String DEFAULT_URL = "http://www.google.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webView = (WebView)findViewById(R.id.webView1);
		urlView = (EditText)findViewById(R.id.urlText);
		messageView = (TextView)findViewById(R.id.message);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("market://")) {
					Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(i);
					return true;
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				messageView.setText("progress : " + newProgress);
			}
		});
		
		webView.loadUrl(DEFAULT_URL);
		urlView.setText(DEFAULT_URL);
		Button btn = (Button)findViewById(R.id.btnGo);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = urlView.getText().toString();
				if (!url.startsWith("http://") && !url.startsWith("https://")) {
					url = "http://" + url;
				}
				webView.loadUrl(url);
			}
		});
		
		btn = (Button)findViewById(R.id.btnBack);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					Toast.makeText(MainActivity.this, "no back history", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnNext);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (webView.canGoForward()) {
					webView.goForward();
				} else {
					Toast.makeText(MainActivity.this, "no forward history", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
