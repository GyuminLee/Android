package org.tacademy.network.rss;

import org.tacademy.network.rss.google.GoogleConstants;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class InAppBrowser extends ParentActivity {
	WebView inAppBrowser;
	
	public static final String FIELD_NAME_URL = "url";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getWindow().requestFeature(Window.FEATURE_PROGRESS);
	    setContentView(R.layout.browser_layout);
	    // TODO Auto-generated method stub
	    Intent urlIntent = getIntent();
	    
	    String url = urlIntent.getStringExtra(FIELD_NAME_URL);
//	    String description = urlIntent.getStringExtra("description");
	    
	    inAppBrowser = (WebView)findViewById(R.id.InWebView);
	    inAppBrowser.getSettings().setJavaScriptEnabled(true);
	    inAppBrowser.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				setTitle(url);
			}
	    	
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("market://")) {
					Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
					startActivity(i);
					return true;
				} else if (url.startsWith(GoogleConstants.REDIRECT_URI)) {
					Uri uri = Uri.parse(url);
					String code = uri.getQueryParameter("code");
					String state = uri.getQueryParameter("state");
					Intent result = new Intent();
					result.putExtra(GoogleConstants.FIELD_NAME_CODE, code);
					result.putExtra(GoogleConstants.FIELD_NAME_STATE, state);
					setResult(RESULT_OK,result);
					finish();
					return true;
				}
				return false;
			}
	    	
	    });
	    inAppBrowser.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				setProgress(newProgress * 100);
			}
	    	
	    });
	    
	    inAppBrowser.loadUrl(url);
//	    inAppBrowser.loadData(description, "text/html", null);
	    
	    Button btn = (Button)findViewById(R.id.back);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (inAppBrowser.canGoBack()) {
					inAppBrowser.goBack();
				}
			}
		});
	    btn = (Button)findViewById(R.id.forward);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (inAppBrowser.canGoForward()) {
					inAppBrowser.goForward();
				}
			}
		});
	    btn = (Button)findViewById(R.id.close);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// activity Á¾·á
				finish();
			}
		});
	}
	@Override
	protected void onPause() {
		super.onPause();
		inAppBrowser.pauseTimers();
	}
	@Override
	protected void onResume() {
		super.onResume();
		inAppBrowser.resumeTimers();
	}
	
	

}
