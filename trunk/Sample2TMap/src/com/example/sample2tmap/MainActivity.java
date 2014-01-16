package com.example.sample2tmap;

import com.skp.Tmap.TMapView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	TMapView mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMap = (TMapView)findViewById(R.id.map);
		new MapRegisterTask().execute("");
	}

	class MapRegisterTask extends AsyncTask<String, Integer, Boolean>{
		@Override
		protected Boolean doInBackground(String... params) {
			mMap.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
			mMap.setLanguage(mMap.LANGUAGE_KOREAN);
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			setUpMap();
		}
	}
	
	private void setUpMap() {
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
