package com.example.samplemelon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity {

	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.listView1);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new MyDownloadTask().execute("");
			}
		});
	}

	class MyDownloadTask extends AsyncTask<String, Integer, MelonResult> {

		@Override
		protected MelonResult doInBackground(String... params) {
			try {
				URL url = new URL(
						"http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("appKey",
						"458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					Gson gson = new Gson();
					InputStreamReader reader = new InputStreamReader(is);
					MelonResult result = gson.fromJson(reader,
							MelonResult.class);
					return result;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(MelonResult result) {
			if (result != null) {
				ArrayAdapter<Song> aa = new ArrayAdapter<Song>(
						MainActivity.this, android.R.layout.simple_list_item_1,
						result.melon.songs.song);
				list.setAdapter(aa);
			} else {
				Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT)
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
