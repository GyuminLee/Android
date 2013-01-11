package com.example.testnetworksample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.testnetworksample.NetworkModel.OnNetworkDownloadListener;

public class MainActivity extends Activity {

	Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				NetworkModel.getInstance().getNetworkData("url", new OnNetworkDownloadListener() {
//					
//					@Override
//					public void onDownloaded() {
//						// TODO Auto-generated method stub
//						Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
//					}
//				},mHandler);
				
				GoogleRequest request = new GoogleRequest();
				NetworkModel.getInstance().getNetworkData(request, new NetworkRequest.OnProcessedListener() {
					
					@Override
					public void onProcessed(NetworkRequest request) {
						// TODO Auto-generated method stub
						GoogleResult r = (GoogleResult)request.getResult();
						Toast.makeText(MainActivity.this, "Result : " + r.result, 
								Toast.LENGTH_SHORT).show();
					}
				}, mHandler);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
