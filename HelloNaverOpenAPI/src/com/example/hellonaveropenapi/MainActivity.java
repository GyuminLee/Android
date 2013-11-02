package com.example.hellonaveropenapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hellonaveropenapi.NetworkModel.OnResultListener;

public class MainActivity extends ParentActivity {

	EditText keywordView;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText) findViewById(R.id.keyword);
		listView = (ListView) findViewById(R.id.listView1);
		Button btn = (Button) findViewById(R.id.btnSearch);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NaverMovieRequest request = new NaverMovieRequest(keyword);
					request.setOnResultListener(new NetworkRequest.OnResultListener<NaverMovies>() {

						@Override
						public void onSuccess(NetworkRequest request, NaverMovies result) {
							MyAdapter aa = new MyAdapter(MainActivity.this, result.item);
							listView.setAdapter(aa);
						}

						@Override
						public void onError(NetworkRequest request, int code) {
							
						}
						
					});
					NetworkModel.getInstance().getNetworkData(MainActivity.this, request);
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
