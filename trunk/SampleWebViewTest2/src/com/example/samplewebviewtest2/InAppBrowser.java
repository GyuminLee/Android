package com.example.samplewebviewtest2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class InAppBrowser extends Activity {

	WebView mWebView;
	public static final String CALLBACK_URL = "";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_PROGRESS);
	    super.onCreate(savedInstanceState);
	
	    setProgressBarVisibility(true);
	    // TODO Auto-generated method stub
	    setContentView(R.layout.in_app_browser);
	    mWebView = (WebView)findViewById(R.id.webView1);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.setWebViewClient(new WebViewClient() {
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		// TODO Auto-generated method stub
	    		if (url.startsWith("http://www.google.com/")) {
	    			Toast.makeText(InAppBrowser.this, "loading google", Toast.LENGTH_SHORT).show();
	    		} else if (url.startsWith("market://")){
	    			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	    			startActivity(i);
	    		} else if (url.startsWith(CALLBACK_URL)) {
	    			// ...
	    		}
	    		return super.shouldOverrideUrlLoading(view, url);
	    	}
	    });
	    mWebView.setWebChromeClient(new WebChromeClient() {
	    	
	    	@Override
	    	public void onProgressChanged(WebView view, int newProgress) {
	    		// TODO Auto-generated method stub
	    		setProgress(newProgress * 100);
	    	}
	    	
	    });
	    
	    mWebView.loadUrl("http://www.google.com/");
	    
	    Button btn = (Button)findViewById(R.id.prev);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mWebView.canGoBack()) {
					mWebView.goBack();
				}
			}
		});
	    
	    btn = (Button)findViewById(R.id.next);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mWebView.canGoForward()) {
					mWebView.goForward();
				}
			}
		});
	    
	
	    mWebView.addJavascriptInterface(jsObject, "myjsobject");
	}



	MyJSObject jsObject = new MyJSObject();
	
	class MyJSObject {
		public String getText() {
			return new String("test");
		}
	}
	
}
