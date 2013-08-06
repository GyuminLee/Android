package com.example.samplenavermovies;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends Activity {

	EditText keywordView;
	TextView titleView;
	ListView listView;
	MovieAdapter mAdapter;
	ImageView showImageView;
	
	
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
			public void onAdapterImageClick(Adapter adapter, View view, ItemData data) {
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
				ItemData data = mAdapter.getItem(position);
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
					
					// search ...
					
					ArrayList<ItemData> movieList = getDummyList();
					mAdapter.clear();
					mAdapter.addAll(movieList);
				}
				
			}
		});
		
		
	}

	private ArrayList<ItemData> getDummyList() {
		ArrayList<ItemData> list = new ArrayList<ItemData>();
		for (int i = 0 ; i < 3; i++) {
			ItemData data = new ItemData();
			data.title = "title" + i;
			data.author = "author" + i;
			data.imageUrl = "url" + i;
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
