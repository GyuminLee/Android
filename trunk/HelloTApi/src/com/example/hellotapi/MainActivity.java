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

import com.google.gson.Gson;

public class MainActivity extends Activity {
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AsyncTask<String, Integer, Melon>() {
					@Override
					protected Melon doInBackground(String... params) {
						try {
							URL url = new URL("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
							HttpURLConnection conn = (HttpURLConnection)url.openConnection();
							conn.setRequestProperty("Accept", "application/json");
							conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
							conn.setConnectTimeout(30000);
							conn.setReadTimeout(3000);
							conn.setRequestMethod("GET");
							int respCode = conn.getResponseCode();
							if (respCode==HttpURLConnection.HTTP_OK) {
								InputStream is = conn.getInputStream();
								Gson gson = new Gson();
								MelonResult result = gson.fromJson(new InputStreamReader(is), MelonResult.class);
								return result.melon;
//								StringBuilder sb = new StringBuilder();
//								String line;
//								BufferedReader br = new BufferedReader(new InputStreamReader(is));
//								while((line = br.readLine()) != null) {
//									sb.append(line);
//									sb.append("\n\r");
//								}
//								return sb.toString();
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
					protected void onPostExecute(Melon result) {
						if (result != null) {
							ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(MainActivity.this, android.R.layout.simple_list_item_1, result.songs.song);
							listView.setAdapter(adapter);
						} else {
							Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
