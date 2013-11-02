package com.example.hellonaveropenapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.begentgroup.xmlparser.XMLParser;

public class MainActivity extends Activity {

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
					new MyDownloadTask().execute(keyword);
				}
			}
		});
	}

	class MyDownloadTask extends AsyncTask<String, Integer, NaverMovies> {

		public static final String URL_PREFIX = "http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&display=10&start=1&target=movie&query=";

		@Override
		protected NaverMovies doInBackground(String... params) {
			String keyword = params[0];
			try {
				URL url = new URL(URL_PREFIX
						+ URLEncoder.encode(keyword, "utf-8"));
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				// conn.setRequestProperty("","");
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					XMLParser parser = new XMLParser();
					NaverMovies movies = parser.fromXml(is, "channel",
							NaverMovies.class);
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
			if (result != null) {
				ArrayAdapter<MovieItem> aa = new ArrayAdapter<MovieItem>(
						MainActivity.this, android.R.layout.simple_list_item_1,
						result.item);
				listView.setAdapter(aa);
			}
			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
