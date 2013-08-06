package com.example.samplenavermovies;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samplenavermovies.MovieAdapter.OnAdapterImageClickListener;
import com.example.samplenavermovies.model.ItemData;
import com.example.samplenavermovies.model.NaverMovieItem;
import com.example.samplenavermovies.model.NaverMovieList;
import com.example.samplenavermovies.model.NaverMovieRequest;
import com.example.samplenavermovies.model.NetworkManager;
import com.example.samplenavermovies.model.NetworkRequest;

public class MainActivity extends Activity {

	EditText keywordView;
	TextView titleView;
	ListView listView;
	MovieAdapter mAdapter;
	ImageView showImageView;
	Handler mHandler = new Handler();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.keyword);
		titleView = (TextView)findViewById(R.id.title);
		listView = (ListView)findViewById(R.id.movieList);
		mAdapter = new MovieAdapter(this);
		listView.setAdapter(mAdapter);
		showImageView = (ImageView)findViewById(R.id.showImageView);
		mAdapter.setOnAdapterImageClickListener(new OnAdapterImageClickListener() {
			
			@Override
			public void onAdapterImageClick(Adapter adapter, View view, NaverMovieItem data) {
				showImageView.setVisibility(View.VISIBLE);
				// ...
				Toast.makeText(MainActivity.this, data.title + " image setting...", Toast.LENGTH_SHORT).show();
			}
		});
		showImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showImageView.setVisibility(View.GONE);
			}
		});
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				NaverMovieItem data = mAdapter.getItem(position);
				Intent i = new Intent(MainActivity.this, WebBrowserActivity.class);
				i.putExtra(WebBrowserActivity.PARAM_TITLE, data.title);
				i.putExtra(WebBrowserActivity.PARAM_URL, data.link);
				startActivity(i);
			}
		});
		
		Button btn = (Button)findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					titleView.setText(keyword);
					
					
					NaverMovieRequest request = new NaverMovieRequest(keyword,1, 10);
					NetworkManager.getInstance().getNetworkData(request, new NetworkRequest.OnCompletedListener() {
						
						@Override
						public void onSuccess(NetworkRequest request, Object result) {
							if (result != null) {
								NaverMovieList list = (NaverMovieList)result;
								mAdapter.addAll(list.items);
							}
						}
						
						@Override
						public void onFail(NetworkRequest request, int errorCode, String errorMsg) {
						}
						
					}, mHandler);
					
//					// search ...
//					NetworkManager.getInstance().getNaverMovieList(keyword, 1, 10, new NetworkManager.OnSimpleNetworkResultListener() {
//						
//						@Override
//						public void onSuccess(String keyword, NaverMovieList list) {
//							mAdapter.addAll(list.items);
//						}
//					}, mHandler);
					
//					ArrayList<NaverMovieItem> movieList = getDummyList();
//					mAdapter.clear();
//					mAdapter.addAll(movieList);
				}
				
			}
		});
		
		
	}

	private ArrayList<NaverMovieItem> getDummyList() {
		ArrayList<NaverMovieItem> list = new ArrayList<NaverMovieItem>();
		for (int i = 0 ; i < 3; i++) {
			NaverMovieItem data = new NaverMovieItem();
			data.title = "title" + i;
			data.director = "author" + i;
			data.image = "url" + i;
			data.link = "link" + i;
		}
		return list;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
