package org.tacademy.basic.googleoauth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    requestWindowFeature(Window.FEATURE_PROGRESS);
	    WebView webview = new WebView(this);
	    setContentView(webview);
	    webview.getSettings().setJavaScriptEnabled(true);
	    Intent intent = getIntent();
	    if (intent.getData() != null) {
	    	webview.loadUrl(intent.getDataString());
	    }
	    webview.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int progress) {
				// TODO Auto-generated method stub
				setProgress(progress * 100);
			}
	    	
	    });
	    
	    webview.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				setTitle(url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				if (url.startsWith(SampleGoogleOAuthActivity.REDIRECT_URI)) {
					Uri uri = Uri.parse(url);
					String code = uri.getQueryParameter("code");
					String state = uri.getQueryParameter("state");
					Intent result = new Intent();
					result.putExtra(SampleGoogleOAuthActivity.FIELD_NAME_CODE, code);
					result.putExtra(SampleGoogleOAuthActivity.FIELD_NAME_STATE, state);
					setResult(RESULT_OK,result);
					finish();
					return true;
				}
				return false;
			}
			
	    });
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		CookieSyncManager.getInstance().stopSync();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CookieSyncManager.getInstance().startSync();
	}
	
	

}
