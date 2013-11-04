package com.example.hellotemptest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.begentgroup.xmlparser.XMLParser;
import com.example.hellotemptest.NetworkRequest.OnResultListener;

public class MainActivity extends Activity {

	EditText cityView;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cityView = (EditText) findViewById(R.id.city);
		listView = (ListView) findViewById(R.id.listView1);
		Button btn = (Button) findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = cityView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					CityRequest request = new CityRequest(keyword);
					request.setOnResultListener(new OnResultListener<Cities>() {

						@Override
						public void onSuccess(NetworkRequest request,
								Cities result) {
							if (result.count == 0) {
								Toast.makeText(MainActivity.this, "not exist city", Toast.LENGTH_SHORT).show();
							} else if (result.count == 1) {
								WeatherForecastRequest req = new WeatherForecastRequest(result.list.item.get(0).toString());
								req.setOnResultListener(new OnResultListener<WeatherData>() {

									@Override
									public void onSuccess(
											NetworkRequest request,
											WeatherData result) {
										MyAdapter aa = new MyAdapter(MainActivity.this, result.forecast.time);
										listView.setAdapter(aa);
									}

									@Override
									public void onError(NetworkRequest request,
											int error) {
										// TODO Auto-generated method stub
										
									}
								});
							} else if (result.count > 1) {
								AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
								builder.setTitle("Select City");
								builder.setIcon(R.drawable.ic_launcher);
								final CharSequence[] items = new CharSequence[result.count];
								for (int i = 0; i < items.length; i++) {
									items[i] = (CharSequence)result.list.item.get(i).toString();
								}
								builder.setItems(items, new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										String city = (String)items[which];
										WeatherForecastRequest req = new WeatherForecastRequest(city);
										req.setOnResultListener(new OnResultListener<WeatherData>() {

											@Override
											public void onSuccess(
													NetworkRequest request,
													WeatherData result) {
												MyAdapter aa = new MyAdapter(MainActivity.this, result.forecast.time);
												listView.setAdapter(aa);
											}

											@Override
											public void onError(
													NetworkRequest request,
													int error) {
												// TODO Auto-generated method stub
												
											}
										});
									}
								});
								builder.create().show();
							}
							
						}

						@Override
						public void onError(NetworkRequest request, int error) {
							
						}
					});
//					new MyTask().execute(keyword);
//					NetworkModel.getInstance().getNetworkData(keyword, new OnResultListener() {
//						
//						@Override
//						public void onSuccess(Object rst) {
//							WeatherData result = (WeatherData)rst;
//							MyAdapter aa = new MyAdapter(MainActivity.this, result.forecast.time);
//							listView.setAdapter(aa);
//						}
//						
//						@Override
//						public void onError() {
//							Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//						}
//					});
					
//					WeatherForecastRequest request = new WeatherForecastRequest(keyword);
//					request.setOnResultListener(new OnResultListener<WeatherData>() {
//
//						@Override
//						public void onSuccess(NetworkRequest request,
//								WeatherData result) {
//							MyAdapter aa = new MyAdapter(MainActivity.this, result.forecast.time);
//							listView.setAdapter(aa);
//						}
//
//						@Override
//						public void onError(NetworkRequest request, int error) {
//							// TODO Auto-generated method stub
//							
//						}
//					});
//					NetworkModel.getInstance().getNetworkData(request);
				}
			}
		});
	}

	class MyTask extends AsyncTask<String, Integer, WeatherData> {
		
		ProgressDialog progress;
		
		@Override
		protected void onPreExecute() {
			progress = new ProgressDialog(MainActivity.this);
			progress.setTitle("Loading Weather...");
			progress.setMessage("message....");
			progress.show();
			super.onPreExecute();
		}
		
		@Override
		protected WeatherData doInBackground(String... params) {
			String city = params[0];
			try {
				URL url = new URL(
						"http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7&q="
								+ city);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int resCode = conn.getResponseCode();
				if (resCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
//					Gson gson = new Gson();
//					WeatherJSONData data = gson.fromJson(new InputStreamReader(
//							is), WeatherJSONData.class);
//					return data;

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
			progress.dismiss();
			
			if (result != null) {
//				ArrayAdapter<WeatherTime> aa = new ArrayAdapter<WeatherTime>(
//						MainActivity.this, android.R.layout.simple_list_item_1,
//						result.forecast.time);
				MyAdapter aa = new MyAdapter(MainActivity.this, result.forecast.time);
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
