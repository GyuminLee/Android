package com.example.sample2navermovie;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	ListView listView;
	EditText keywordView;
	MovieAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		keywordView = (EditText) findViewById(R.id.editText1);
		Button btn = (Button) findViewById(R.id.btnSearch);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					MovieDialogFragment mdf = new MovieDialogFragment();
					MovieRequest request = new MovieRequest(keyword);
					request.setOnResultListener(new MovieRequest.OnResultListener() {
						
						@Override
						public void onSuccess(MovieRequest request, NaverMovies movies) {
							mAdapter = new MovieAdapter(MainActivity.this, movies.item);
							listView.setAdapter(mAdapter);
						}
						
						@Override
						public void onError(MovieRequest request, int errorCode) {
							Toast.makeText(MainActivity.this, "fail...", Toast.LENGTH_SHORT).show();
						}
					});
					mdf.setOnNetworkResultListener(request);
					mdf.show(getSupportFragmentManager(), "dialog");
				} else {
					Toast.makeText(MainActivity.this, "insert keyword...", Toast.LENGTH_SHORT).show();
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
