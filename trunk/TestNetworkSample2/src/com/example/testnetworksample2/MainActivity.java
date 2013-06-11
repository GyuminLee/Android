package com.example.testnetworksample2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText urlView;
	TextView messageView;
	Handler mHandler = new Handler();
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		urlView = (EditText)findViewById(R.id.urlText);
		messageView = (TextView)findViewById(R.id.message);
		list = (ListView)findViewById(R.id.listView1);
		Button btn = (Button)findViewById(R.id.go);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String urlString = urlView.getText().toString();
				if (urlString != null && !urlString.equals("")) {
					
					NaverMovieRequest request = new NaverMovieRequest(urlString);
					NetworkModel.getInstance().getNetworkData(request, new NetworkRequest.OnProcessCompleteListener() {
						
						@Override
						public void onError(NetworkRequest request, String errorMessage) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onCompleted(NetworkRequest request) {
							// TODO Auto-generated method stub
							
						}
					}, mHandler);
					
					
//					String url = null;
//					if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
//						url = urlString;
//					} else {
//						url = "http://" + urlString;
//					}
//					NetworkUrlRequest request = new NetworkUrlRequest(url,mHandler);
//					request.setOnDownloadCompleteListener(new OnDownloadCompleteListener() {
//						
//						@Override
//						public void onError(NetworkUrlRequest request, String errorMessage) {
//							Toast.makeText(MainActivity.this, "error Message : " + errorMessage, Toast.LENGTH_SHORT).show();
//						}
//						
//						@Override
//						public void onCompleted(NetworkUrlRequest request) {
//							messageView.setText(request.getResult());
//						}
//					});
//					request.start();

//					UrlRequest request = new UrlRequest(url);
//					NetworkModel.getInstance().getNetworkData(request, new NetworkRequest.OnProcessCompleteListener() {
//						
//						@Override
//						public void onError(NetworkRequest request, String errorMessage) {
//							Toast.makeText(MainActivity.this, "error Message : " + errorMessage, Toast.LENGTH_SHORT).show();
//						}
//						
//						@Override
//						public void onCompleted(NetworkRequest request) {
//							UrlRequest rq = (UrlRequest)request;
//							String message = rq.getResult();
//							messageView.setText(message);
//						}
//					}, mHandler);
//					MelonRequest request = new MelonRequest();
//					NetworkModel.getInstance().getNetworkData(request, new NetworkRequest.OnProcessCompleteListener() {
//						
//						@Override
//						public void onError(NetworkRequest request, String errorMessage) {
//							// TODO Auto-generated method stub
//							
//						}
//						
//						@Override
//						public void onCompleted(NetworkRequest request) {
//							MelonRequest rq = (MelonRequest)request;
//							Melon melon = rq.getResult();
//							ArrayAdapter<Song> aa = new ArrayAdapter<Song>(MainActivity.this, android.R.layout.simple_list_item_1, melon.songs.song);
//							list.setAdapter(aa);
//						}
//					}, mHandler);
					
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
