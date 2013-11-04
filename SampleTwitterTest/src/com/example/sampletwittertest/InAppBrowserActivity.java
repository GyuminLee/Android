package com.example.sampletwittertest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InAppBrowserActivity extends Activity {

	WebView webView;
	public static final String PARAM_URL = "url";
	public static final String RETURN_VERIFIER = "verifier";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    webView = new WebView(this);
	    setContentView(webView);
	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.setWebViewClient(new WebViewClient(){
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		if (url.startsWith(MainActivity.CALLBACK_URL)) {
	    			Uri uri = Uri.parse(url);
	    			String verifier = uri.getQueryParameter("oauth_verifier");
	    			Intent data = new Intent();
	    			data.putExtra(RETURN_VERIFIER, verifier);
	    			setResult(RESULT_OK,data);
	    			finish();
	    			return true;
	    		}
	    		return super.shouldOverrideUrlLoading(view, url);
	    	}
	    });
	    webView.setWebChromeClient(new WebChromeClient(){});
	    String url = getIntent().getStringExtra(PARAM_URL);
	    webView.loadUrl(url);
	}
}
