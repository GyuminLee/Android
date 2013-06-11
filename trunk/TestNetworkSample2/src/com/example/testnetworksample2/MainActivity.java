package com.example.testnetworksample2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testnetworksample2.NetworkUrlRequest.OnDownloadCompleteListener;

public class MainActivity extends Activity {

	EditText urlView;
	TextView messageView;
	Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		urlView = (EditText)findViewById(R.id.urlText);
		messageView = (TextView)findViewById(R.id.message);
		Button btn = (Button)findViewById(R.id.go);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String urlString = urlView.getText().toString();
				if (urlString != null && !urlString.equals("")) {
					String url = null;
					if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
						url = urlString;
					} else {
						url = "http://" + urlString;
					}
					NetworkUrlRequest request = new NetworkUrlRequest(url,mHandler);
					request.setOnDownloadCompleteListener(new OnDownloadCompleteListener() {
						
						@Override
						public void onError(NetworkUrlRequest request, String errorMessage) {
							Toast.makeText(MainActivity.this, "error Message : " + errorMessage, Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onCompleted(NetworkUrlRequest request) {
							messageView.setText(request.getResult());
						}
					});
					request.start();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
