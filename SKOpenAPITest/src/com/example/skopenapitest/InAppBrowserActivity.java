package com.example.skopenapitest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InAppBrowserActivity extends Activity {

	public static final String PARAM_URL = "url";
	public static final String RESULT_ACCESS_TOKEN = "token";
	
	WebView webView;
	String url;
	String redirectUri;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.web_view);
	    webView = (WebView)findViewById(R.id.webView1);
	    Intent i = getIntent();
	    url = i.getStringExtra(PARAM_URL);
	    webView.setWebViewClient(new WebViewClient(){
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		if (url.startsWith(redirectUri)) {
	    			Uri uri = Uri.parse(url);
	    			String fragment = uri.getFragment();
	    			String accessToken = null;
	    			for (String param : fragment.split("&")) {
	    				String[] p = param.split("=");
	    				if (p.length == 2 && p[0].equals("access_token")) {
	    					accessToken = p[1];
	    					break;
	    				}
	    			}
	    			if (accessToken != null) {
	    				Intent data = new Intent();
	    				data.putExtra(RESULT_ACCESS_TOKEN, accessToken);
	    				setResult(RESULT_OK, data);
	    			} else {
	    				setResult(RESULT_CANCELED);
	    			}
	    			finish();
	    			return true;
	    		}
	    		return false;
	    	}
	    });
	    webView.setWebChromeClient(new WebChromeClient(){
	    	
	    });
	    
	    Uri uri = Uri.parse(url);
	    redirectUri = uri.getQueryParameter("redirect_uri");
	    webView.loadUrl(url);
	}

}
