package com.example.hellotapi;

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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends Activity {
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<String, Integer, CategoryResponse>() {
					@Override
					protected CategoryResponse doInBackground(String... params) {
						try {
							URL url = new URL(
									"https://apis.skplanetx.com/tmap/routes?callback=&bizAppId=&version=1&endX=129.07579349764512&endY=35.17883196265564&startX=126.98217734415019&startY=37.56468648536046&resCoordType=WGS84GEO&reqCoordType=WGS84GEO");
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setRequestProperty("Accept",
									"application/json");
							conn.setRequestProperty("appKey",
									"458a10f5-c07e-34b5-b2bd-4a891e024c2a");
							conn.setConnectTimeout(30000);
							conn.setReadTimeout(3000);
							conn.setRequestMethod("GET");
							int respCode = conn.getResponseCode();
							if (respCode == HttpURLConnection.HTTP_OK) {
								InputStream is = conn.getInputStream();
								Gson gson = new GsonBuilder().registerTypeAdapter(Geometry.class, new GeometryDeseriaizer()).create(); 
										//new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create(); //new Gson();
								CategoryResult result = gson.fromJson(
										new InputStreamReader(is),
										CategoryResult.class);
								return result.categoryResponse;
								// StringBuilder sb = new StringBuilder();
								// String line;
								// BufferedReader br = new BufferedReader(new
								// InputStreamReader(is));
								// while((line = br.readLine()) != null) {
								// sb.append(line);
								// sb.append("\n\r");
								// }
								// return sb.toString();
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
					protected void onPostExecute(CategoryResponse result) {
						if (result != null) {
							ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(
									MainActivity.this,
									android.R.layout.simple_list_item_1,
									result.children.category);
							listView.setAdapter(adapter);
						} else {
							Toast.makeText(MainActivity.this, "Error",
									Toast.LENGTH_SHORT).show();
						}
					}
				}.execute("");

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
