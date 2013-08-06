package com.example.samplenavermovies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebBrowserActivity extends Activity {

	WebView webView;
	
	public static final String PARAM_TITLE = "title";
	public static final String PARAM_URL = "url";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    requestWindowFeature(Window.FEATURE_PROGRESS);
	
	    setContentView(R.layout.browser_activity);
	    webView = (WebView)findViewById(R.id.webView1);
	    
	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.setWebViewClient(new WebViewClient() {
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		if (url.startsWith("marker://")) {
	    			Intent i = new Intent(Intent.ACTION_VIEW,
	    					Uri.parse(url));
	    			startActivity(i);
	    			return true;
	    		}
	    		setProgressBarVisibility(true);
	    		return super.shouldOverrideUrlLoading(view, url);
	    	}
	    
	    });
	    webView.setWebChromeClient(new WebChromeClient() {
	    	@Override
	    	public void onProgressChanged(WebView view, int newProgress) {
	    		setProgress(newProgress * 100);
	    		if (newProgress > 99) {
	    			setProgressBarVisibility(false);
	    		}
	    	}
	    });
	    Intent intent = getIntent();
	    String title = intent.getStringExtra(PARAM_TITLE);
	    String url = intent.getStringExtra(PARAM_URL);
	    setTitle(title);
	    webView.loadUrl(url);
	    
	    Button btn = (Button)findViewById(R.id.btnClose);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	    
	}

}
