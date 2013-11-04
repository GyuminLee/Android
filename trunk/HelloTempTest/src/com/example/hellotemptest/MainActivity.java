package com.example.hellotemptest;

import java.io.IOException;
import java.io.InputStream;
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
import android.widget.EditText;
import android.widget.ListView;

import com.begentgroup.xmlparser.XMLParser;

public class MainActivity extends Activity {

	EditText cityView;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cityView = (EditText) findViewById(R.id.city);
		listView = (ListView) findViewById(R.id.listView1);
		Button btn = (Button)findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = cityView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					new MyTask().execute(keyword);
				}				
			}
		});
	}

	class MyTask extends AsyncTask<String, Integer, WeatherData> {
		@Override
		protected WeatherData doInBackground(String... params) {
			String city = params[0];
			try {
				URL url = new URL(
						"http://api.openweathermap.org/data/2.5/forecast/daily?mode=xml&units=metric&cnt=7&q="
								+ city);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					XMLParser parser = new XMLParser();
					WeatherData data = parser.fromXml(is, "weatherdata",
							WeatherData.class);
					return data;
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
		protected void onPostExecute(WeatherData result) {
			if (result != null) {
				ArrayAdapter<WeatherTime> aa = new ArrayAdapter<WeatherTime>(
						MainActivity.this, android.R.layout.simple_list_item_1,
						result.forecast.time);
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
