package com.example.samplexmltest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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

	ListView list;
	EditText keyword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list = (ListView)findViewById(R.id.listView1);
		keyword = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
	}
	
	class MyTask extends AsyncTask<String, Integer, NaverMovies> {
		@Override
		protected NaverMovies doInBackground(String... params) {
			String keyword = params[0];
			try {
				URL url = new URL("http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&query=%EB%B2%A4%ED%97%88&display=10&start=1&target=movie");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					XMLParser parser = new XMLParser();
					NaverMovies movies = parser.fromXml(is, "channel", NaverMovies.class);
					return movies;
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
		protected void onPostExecute(NaverMovies result) {
			super.onPostExecute(result);
			if (result != null) {
				ArrayAdapter<MovieItem> adapter = new ArrayAdapter<MovieItem>(MainActivity.this, android.R.layout.simple_list_item_1, result.item);
				list.setAdapter(adapter);
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
