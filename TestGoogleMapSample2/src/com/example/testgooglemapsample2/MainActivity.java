package com.example.testgooglemapsample2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity {

	GoogleMap mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeed();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeed();
	}
	
	private void setUpMapIfNeed() {
		if (mMap == null) {
			mMap = ((SupportMapFragment)getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
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
