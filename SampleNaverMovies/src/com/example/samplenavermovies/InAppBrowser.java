package com.example.samplenavermovies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class InAppBrowser extends Activity {

	WebView webView;
	
	private String callbackURL;
	private String codeString;
	public static final String PARAM_URL = "url";
	public static final String PARAM_CALLBACK_URL = "callback";
	public static final String PARAM_CODE_STRING = "code";
	public static final String RETURN_CODE = "return_code";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.in_app_browser);
	    
	    webView = (WebView)findViewById(R.id.webView1);
	    Button btn = (Button)findViewById(R.id.btnClose);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	    
	    webView.getSettings().setJavaScriptEnabled(true);
	    
	    Intent i = getIntent();
	    String url = i.getStringExtra(PARAM_URL);
	    callbackURL = i.getStringExtra(PARAM_CALLBACK_URL);
	    codeString = i.getStringExtra(PARAM_CODE_STRING);

	    webView.setWebViewClient(new WebViewClient(){
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		if (url.startsWith(callbackURL)) {
	    			Uri uri = Uri.parse(url);
	    			String code = uri.getQueryParameter(codeString);
	    			if (code != null && !code.equals("")) {
	    				Intent data = new Intent();
	    				data.putExtra(RETURN_CODE, code);
	    				setResult(RESULT_OK, data);
	    			} else {
	    				setResult(RESULT_CANCELED);
	    			}
	    			finish();
	    			return true;
	    		}
	    		return super.shouldOverrideUrlLoading(view, url);
	    	}
	    });
	    webView.setWebChromeClient(new WebChromeClient(){});
	    
	    
	    
	    
	}

}
