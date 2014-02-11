package com.example.sampleloadmoredata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.begentgroup.xmlparser.XMLParser;

public class MainActivity extends Activity {

	private static final int NOT_SETTING = -1;
	int start = 1;
	int count = 10;
	int total = NOT_SETTING;
	
	ArrayList<MovieItem> mList = new ArrayList<MovieItem>();
	ArrayAdapter<MovieItem> mAdapter;
	ListView listView;
	EditText keywordView;
	String mKeyword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		keywordView = (EditText) findViewById(R.id.editText1);
		mAdapter = new ArrayAdapter<MovieItem>(this, android.R.layout.simple_list_item_1, mList);
		listView.setAdapter(mAdapter);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
					if (!isLoading) {
						isLoading = true;
						loadData(null);
					}
				}
			}
		});
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					loadData(keyword);
				}
			}
		});
	}

	boolean isLoading = false;
	
	private void loadData(String keyword) {
		if (keyword != null
				&& !keyword.equals("")
				&& (mKeyword == null || mKeyword.equals("") || !mKeyword
						.equals(keyword))) {
			start = 1;
			mKeyword = keyword;
		}
		
		if (mKeyword != null && !mKeyword.equals("") && (total == NOT_SETTING || start < total)) {
			new MyTask().execute(mKeyword);
		} else {
			isLoading = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class MyTask extends AsyncTask<String, Integer, NaverMovies> {
		@Override
		protected NaverMovies doInBackground(String... params) {
			String keyword = params[0];
			try {
				URL url = new URL(
						"http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display="+count+"&start="
								+ start
								+ "&target=movie&query="
								+ URLEncoder.encode(keyword, "utf8"));
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					XMLParser parser = new XMLParser();
					NaverMovies movies = parser.fromXml(conn.getInputStream(),
							"channel", NaverMovies.class);
					return movies;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(NaverMovies result) {
			isLoading = false;
			if (result != null) {
				start = result.start + result.display;
				total = result.total;
				mAdapter.addAll(result.item);
			}
			super.onPostExecute(result);
		}
	}

}
