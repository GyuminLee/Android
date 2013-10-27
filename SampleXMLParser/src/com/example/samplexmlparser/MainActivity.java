package com.example.samplexmlparser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.begentgroup.xmlparser.XMLParser;

public class MainActivity extends Activity {

	String movieUrl = "http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&display=10&start=1&target=movie&query=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new MyMovieTask().execute("벤허");
		
	}

	class MyMovieTask extends AsyncTask<String, Integer, Movies> {
		@Override
		protected Movies doInBackground(String... params) {
			try {
				String keyword = URLEncoder.encode(params[0], "utf-8");
				URL url = new URL(movieUrl + keyword);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					XMLParser parser = new XMLParser();
					Movies movies = parser.fromXml(conn.getInputStream(), "channel", Movies.class);
					return movies;
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		protected void onPostExecute(Movies result) {
			if (result != null) {
				Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
				XMLParser parser = new XMLParser();
				String message = parser.toXML(result, "channel", "<rss>", "</rss>");
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
				TextView tv = (TextView)findViewById(R.id.textView1);
				tv.setText(message);
			} else {
				Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
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
