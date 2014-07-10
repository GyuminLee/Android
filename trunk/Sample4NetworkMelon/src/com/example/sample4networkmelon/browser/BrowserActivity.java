package com.example.sample4networkmelon.browser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.sample4networkmelon.R;

public class BrowserActivity extends ActionBarActivity {

	private static final String TAG = "BrowserActivity";
	
	WebView webView;
	ActionBar actionBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browser_activity);
	    actionBar = getSupportActionBar();
	    webView = (WebView)findViewById(R.id.webView1);
	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.setWebViewClient(new WebViewClient() { 
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		if (url.startsWith("http://www.google.com")) {
	    			// ...
	    			return true;
	    		} else if (url.startsWith("market://")) {
	    			Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
	    			startActivity(i);
	    		}
	    		return super.shouldOverrideUrlLoading(view, url);
	    	}
	    	
	    });
	    
	    webView.setWebChromeClient(new WebChromeClient() {
	    	@Override
	    	public void onProgressChanged(WebView view, int newProgress) {
	    		super.onProgressChanged(view, newProgress);
	    		Log.i(TAG,"progress : " + newProgress);
	    	}
	    });
	    
	    Intent i = getIntent();
	    Uri uri = i.getData();
	    webView.loadUrl(uri.toString());
	    
	    Button btn = (Button)findViewById(R.id.btn_prev);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					Toast.makeText(BrowserActivity.this, "no history", Toast.LENGTH_SHORT).show();
				}
			}
		});
	    
	    btn = (Button)findViewById(R.id.btn_next);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (webView.canGoForward()) {
					webView.goForward();
				} else {
					Toast.makeText(BrowserActivity.this, "no history", Toast.LENGTH_SHORT).show();
				}		
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		webView.resumeTimers();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		webView.pauseTimers();
	}

}
