package com.example.sampleskpoauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InAppBrowserActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.in_app_browser);
	    String url = getIntent().getStringExtra("url");
	    WebView webView = (WebView)findViewById(R.id.webView1);
	    webView.setWebChromeClient(new WebChromeClient(){});
	    webView.setWebViewClient(new WebViewClient() {
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		if (url.startsWith("http://localhost/")) {
	    			Uri uri = Uri.parse(url);
	    			String code = uri.getQueryParameter("code");
	    			Intent data = new Intent();
	    			data.putExtra("result", code);
	    			setResult(RESULT_OK, data);
	    			finish();
	    			return true;
	    		}
	    		return false;
	    	}
	    });
	    webView.loadUrl(url);
	}

}
