package org.tacademy.pjt.daum;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class InAppBrowser extends Activity {

	WebView webView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.in_app_browser);
	    // TODO Auto-generated method stub
	    webView = (WebView)findViewById(R.id.webView1);
	    
	    webView.getSettings().setJavaScriptEnabled(true);
	    
	    webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("http://dongjaguestbook.appspot.com/guestbook")) {
					Toast.makeText(InAppBrowser.this, "url:"+ url, Toast.LENGTH_LONG).show();
					Intent i = new Intent();
					Uri uri = Uri.parse(url);
					String verifier = uri.getQueryParameter("oauth_verifier");
					i.putExtra("verifier", verifier);
					setResult(Activity.RESULT_OK,i);
					finish();
					return true;
				}
				return false;
			}
			
	    	
	    });
	    
	    webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}
	    	
	    });
	    
	    Intent i = getIntent();
	    String url = i.getStringExtra("url");
	    webView.loadUrl(url);
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
