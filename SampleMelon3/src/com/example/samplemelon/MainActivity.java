package com.example.samplemelon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
import android.widget.ListView;

import com.google.gson.Gson;

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
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			listView = (ListView)rootView.findViewById(R.id.listView1);
			
			Button btn = (Button)rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new MyTask().execute();
				}
			});
			return rootView;
		}
		
		class MyTask extends AsyncTask<Integer, Integer, MelonResult> {
			@Override
			protected MelonResult doInBackground(Integer... params) {
				try {
					URL url = new URL("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					conn.setConnectTimeout(30000);
					conn.setReadTimeout(3000);
					conn.setRequestProperty("Accept", "application/json");
					conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						Gson gson = new Gson();
						InputStreamReader isr = new InputStreamReader(is);
						MelonResult mr = gson.fromJson(isr, MelonResult.class);
//						BufferedReader br = new BufferedReader(new InputStreamReader(is));
//						StringBuilder sb = new StringBuilder();
//						String line;
//						while((line = br.readLine()) != null) {
//							sb.append(line);
//							sb.append("\n\r");
//						}
						return mr;
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
				super.onPostExecute(result);
				if (result != null) {
					ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(getActivity(), android.R.layout.simple_list_item_1, result.melon.songs.song);
					listView.setAdapter(adapter);
//					Toast.makeText(getActivity(), "result : " + result, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

}
