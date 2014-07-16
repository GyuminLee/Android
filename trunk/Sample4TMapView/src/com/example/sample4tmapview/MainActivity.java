package com.example.sample4tmapview;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.skp.Tmap.TMapView;

public class MainActivity extends Activity {

	TMapView mapView;
	private static final String APP_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (TMapView)findViewById(R.id.mapView);
		new RegisterTask().execute();
	}
	
	class RegisterTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			mapView.setSKPMapApiKey(APP_KEY);
			mapView.setLanguage(mapView.LANGUAGE_KOREAN);
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result != null && result) {
				initailizeMap();
			}
		}
	}
	
	private void initailizeMap() {
		
	}
	
	
}
