package com.example.sample4applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class BrowserActivity extends Activity {

	WebView webView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browser_layout);
	    webView = (WebView)findViewById(R.id.webView1);
	    Intent i = getIntent();
	    Uri uri = i.getData();
	    String url = uri.toString();
	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.setWebViewClient(new WebViewClient());
	    webView.setWebChromeClient(new WebChromeClient());
	    webView.loadUrl(url);
	    Toast.makeText(this, "Browser Activity", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		webView.pauseTimers();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		webView.resumeTimers();
	}

}
