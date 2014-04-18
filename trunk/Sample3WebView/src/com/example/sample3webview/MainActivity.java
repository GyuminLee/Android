package com.example.sample3webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		WebView mWebView;
		EditText urlView;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			mWebView = (WebView)rootView.findViewById(R.id.webView1);
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (url.startsWith("market://")) {
						Intent uri = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						startActivity(uri);
						return true;
					} else if (url.startsWith("http://www.google.com")) {
						Toast.makeText(getActivity(), "google!!!", Toast.LENGTH_SHORT).show();
					}
					return super.shouldOverrideUrlLoading(view, url);
				}
			});
			mWebView.setWebChromeClient(new WebChromeClient(){
				@Override
				public void onProgressChanged(WebView view, int newProgress) {
					super.onProgressChanged(view, newProgress);
					Log.i("MainActivity","webview progress : " + newProgress);
				}
			});
			mWebView.loadUrl("http://www.google.com");
			Button btn = (Button)rootView.findViewById(R.id.btnGo);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String url = urlView.getText().toString();
					if (!url.startsWith("http://") && !url.startsWith("https://")) {
						url = "http://" + url;
					}
					mWebView.loadUrl(url);
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mWebView.canGoBack()) {
						mWebView.goBack();
					}
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.button2);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mWebView.canGoForward()) {
						mWebView.goForward();
					}
				}
			});
			return rootView;
		}

		@Override
		public void onResume() {
			super.onResume();
			mWebView.resumeTimers();
		}
		
		@Override
		public void onPause() {
			super.onPause();
			mWebView.pauseTimers();
		}
	}

}
