package com.example.sample2navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.begentgroup.xmlparser.XMLParser;

public class MainActivity extends Activity {

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
				if (dialog == null) {
					dialog = new ProgressDialog(MainActivity.this);
					dialog.setIcon(R.drawable.ic_launcher);
					dialog.setTitle("Download Movie");
					dialog.setMessage("Please wait...");
				}
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					new MyMovieTask().execute(keyword);
					dialog.show();
				} else {
					Toast.makeText(MainActivity.this, "insert keyword...", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	ProgressDialog dialog;

	class MyMovieTask extends AsyncTask<String, Integer, NaverMovies> {
		@Override
		protected NaverMovies doInBackground(String... params) {
			HttpURLConnection conn = null;
			InputStream is = null;
			String keyword = params[0];
			try {
				URL url = new URL(
						"http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=movie&query="
								+ URLEncoder.encode(keyword, "utf8"));
				conn = (HttpURLConnection) url.openConnection();

				// conn.setRequestMethod("POST");
				// conn.setRequestProperty("accept", "application/xml");
				// conn.setChunkedStreamingMode(8096);
				// OutputStream os = conn.getOutputStream();

				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					is = conn.getInputStream();
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
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(NaverMovies result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			if (result != null) {
				mAdapter = new MovieAdapter(MainActivity.this, result.item);
				listView.setAdapter(mAdapter);
			} else {
				Toast.makeText(MainActivity.this, "error!", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
