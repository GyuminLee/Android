package com.example.testnavermovies;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText keywordView;
	ListView listView;
	MyAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keywordView = (EditText)findViewById(R.id.editKeyword);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new MyAdapter(this);
        listView.setAdapter(mAdapter);
        Button btn = (Button)findViewById(R.id.btnSearch);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					new MySearchTask().execute(keyword);
				}
			}
		});
    }

    class MySearchTask extends AsyncTask<String, Integer, Movies> {
    	@Override
    	protected Movies doInBackground(String... params) {
    		String keyword = params[0];
    		try {
				URL url = new URL("http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=99&start=1&target=movie&query="+URLEncoder.encode(keyword,"utf8"));
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					XMLParser parser = new XMLParser();
					Movies movies = parser.fromXml(is, "channel", Movies.class);
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
    	protected void onPostExecute(Movies result) {
    		super.onPostExecute(result);
    		if (result != null) {
    			mAdapter.addAll(result.item);
    		} else {
    			Toast.makeText(MainActivity.this, "No Result", Toast.LENGTH_SHORT).show();
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
