package com.example.sampleweathercode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.begentgroup.xmlparser.XMLParser;
import com.google.gson.Gson;

public class MainActivity extends Activity {

	ListView listView;
	
	private static final String TOP_URL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";
	private static final String MIDDLE_URL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl.%s.json.txt";
	private static final String LEAF_URL = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf.%s.json.txt";
	private static final String WEATHER_URL = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=%s&gridy=%s";
	
	Spinner topSpinner, middleSpinner, leafSpinner;
	ArrayAdapter<AreaCode> topAdapter, middleAdapter, leafAdapter;
	
	ArrayAdapter<WeatherData> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<WeatherData>(this, android.R.layout.simple_list_item_1, new ArrayList<WeatherData>());
		listView.setAdapter(mAdapter);
		topSpinner = (Spinner)findViewById(R.id.top_spinner);
		middleSpinner = (Spinner)findViewById(R.id.middle_spinner);
		leafSpinner = (Spinner)findViewById(R.id.leaf_spinner);
		topAdapter = new ArrayAdapter<AreaCode>(this, android.R.layout.simple_spinner_item, new ArrayList<AreaCode>());
		topAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		middleAdapter = new ArrayAdapter<AreaCode>(this, android.R.layout.simple_spinner_item, new ArrayList<AreaCode>());
		middleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		leafAdapter = new ArrayAdapter<AreaCode>(this, android.R.layout.simple_spinner_item, new ArrayList<AreaCode>());
		leafAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		topSpinner.setAdapter(topAdapter);
		middleSpinner.setAdapter(middleAdapter);
		leafSpinner.setAdapter(leafAdapter);
		
		topSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				AreaCode code = (AreaCode)topSpinner.getItemAtPosition(position);
				final String url = String.format(MIDDLE_URL, code.code);
				new MyTask(new OnResultListener<String, AreaCode[]>() {

					@Override
					public void onSuccess(String request, AreaCode[] result) {
						if (request == url) {
							middleAdapter.clear();
							for (AreaCode code : result) {
								middleAdapter.add(code);
							}
							middleSpinner.setSelection(0);
						}
					}

					@Override
					public void onError(String request, int code) {
												
					}
					
				}, url).execute(url);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		middleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				AreaCode code = (AreaCode)middleSpinner.getItemAtPosition(position);
				final String url = String.format(LEAF_URL, code.code);
				new MyTask(new OnResultListener<String, AreaCode[]>() {

					@Override
					public void onSuccess(String request, AreaCode[] result) {
						if (request == url) {
							leafAdapter.clear();
							for (AreaCode code : result) {
								leafAdapter.add(code);
							}
							leafSpinner.setSelection(0);
						}
						
					}

					@Override
					public void onError(String request, int code) {
						// TODO Auto-generated method stub
						
					}
				}, url).execute(url);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		leafSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				AreaCode code = (AreaCode)leafSpinner.getItemAtPosition(position);
				String url = String.format(WEATHER_URL, code.x, code.y);
				new WeatherTask().execute(url);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		initData();
		
	}
	
	private void initData() {
		new MyTask(new OnResultListener<String, AreaCode[]>() {

			@Override
			public void onSuccess(String request, AreaCode[] result) {
				if (request == TOP_URL) {
					topAdapter.clear();
					for (AreaCode code : result) {
						topAdapter.add(code);
					}
				}
			}

			@Override
			public void onError(String request, int code) {
				Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
			}
		}, TOP_URL).execute(TOP_URL);
	}
	
	public interface OnResultListener<E,T> {
		public void onSuccess(E request, T result);
		public void onError(E request, int code);
	}
	
	class MyTask extends AsyncTask<String, Integer, AreaCode[]> {
	
		String mRequest;
		OnResultListener<String, AreaCode[]> mListener;
		public MyTask(OnResultListener<String,AreaCode[]> listener, String request) {
			super();
			mRequest = request;
			mListener = listener;
		}
		
		@Override
		protected AreaCode[] doInBackground(String... params) {
			String urlString = params[0];
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					Gson gson = new Gson();
					AreaCode[] list = gson.fromJson(new InputStreamReader(conn.getInputStream()), AreaCode[].class); 
					return list;
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
		protected void onPostExecute(AreaCode[] result) {
			super.onPostExecute(result);
			if (result != null) {
				if (mListener != null) {
					mListener.onSuccess(mRequest, result);
				}
			} else {
				if (mListener != null) {
					mListener.onError(mRequest, 0);
				}
			}
		}
	}
	
	class WeatherTask extends AsyncTask<String, Integer, Weather> {
		@Override
		protected Weather doInBackground(String... params) {
			String urlString = params[0];
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf8")));
//					String line;
//					StringBuilder sb = new StringBuilder();
//					while((line=br.readLine()) != null) {
//						sb.append(line + "\n\r");
//					}
//					return sb.toString();
					XMLParser parser = new XMLParser();
					Weather w = parser.fromXml(conn.getInputStream(), "wid", Weather.class);
					return w;
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
		protected void onPostExecute(Weather result) {
			if (result != null) {
				setTitle(result.header.tm);
				mAdapter.clear();
				for (WeatherData d : result.body.data) {
					mAdapter.add(d);
				}
			}
		}
	}
	
}
