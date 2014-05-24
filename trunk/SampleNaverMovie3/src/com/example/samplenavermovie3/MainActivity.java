package com.example.samplenavermovie3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		ListView listView;
		EditText keywordView;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			keywordView = (EditText)rootView.findViewById(R.id.editText1);
			listView = (ListView)rootView.findViewById(R.id.listView1);
			Button btn = (Button)rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new MyTask().execute(keywordView.getText().toString());
				}
			});
			return rootView;
		}
		
		class MyTask extends AsyncTask<String, Integer, NaverMovie> {
			@Override
			protected NaverMovie doInBackground(String... params) {
				String keyword = params[0];
				try {
					URL url = new URL("http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=movie&query="+URLEncoder.encode(keyword, "utf8"));
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					conn.setConnectTimeout(30000);
					conn.setReadTimeout(30000);
					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						XMLParser parser = new XMLParser();
						NaverMovie movies = parser.fromXml(is, "channel", NaverMovie.class);
						return movies;
//						BufferedReader br = new BufferedReader(new InputStreamReader(is));
//						StringBuilder sb = new StringBuilder();
//						String line;
//						while((line = br.readLine()) != null) {
//							sb.append(line + "\n\r");
//						}
//						return sb.toString();
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
			protected void onPostExecute(NaverMovie result) {
				super.onPostExecute(result);
				if (result != null) {
//					ArrayAdapter<MovieItem> adapter = new ArrayAdapter<MovieItem>(getActivity(), android.R.layout.simple_list_item_1, result.item);
					MyAdapter adapter = new MyAdapter(getActivity(),result.item);
					listView.setAdapter(adapter);
//					Toast.makeText(getActivity(), "result " + result, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

}
