package com.example.samplenavermovies;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.samplenavermovies.model.GooglePlaceItem;
import com.example.samplenavermovies.model.GooglePlaceList;
import com.example.samplenavermovies.model.GooglePlaceRequest;
import com.example.samplenavermovies.model.NetworkManager;
import com.example.samplenavermovies.model.NetworkRequest;
import com.example.samplenavermovies.model.NetworkRequest.OnCompletedListener;

public class ShowGoogleActivity extends ParentActivity implements LocationListener {

	EditText keyword;
	ListView listView;
	
	LocationManager mLM;
	Location mCurrentLocation = null;
	Handler mHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.google_layout);
	    keyword = (EditText)findViewById(R.id.editText1);
	    listView = (ListView)findViewById(R.id.listView1);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mCurrentLocation != null) {
					String k = keyword.getText().toString();
					if (k != null && !k.equals("")) {
						GooglePlaceRequest request = new GooglePlaceRequest(k, mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
						NetworkManager.getInstance().getNetworkData(ShowGoogleActivity.this, request, new OnCompletedListener() {
							
							@Override
							public void onSuccess(NetworkRequest request, Object result) {
								if (result != null) {
									GooglePlaceList list = (GooglePlaceList)result;
									ArrayAdapter<GooglePlaceItem> aa = new ArrayAdapter<GooglePlaceItem>(ShowGoogleActivity.this, android.R.layout.simple_list_item_1, list.results);
									listView.setAdapter(aa);
								}
							}
							
							@Override
							public void onFail(NetworkRequest request, int errorCode, String errorMsg) {
							}
						}, mHandler);
					}
				} else {
					Toast.makeText(ShowGoogleActivity.this, "Location not fixed", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	    mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		mCurrentLocation = mLM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	}


	@Override
	protected void onStop() {
		mLM.removeUpdates(this);
		super.onStop();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		mCurrentLocation = location;
	}


	@Override
	public void onProviderDisabled(String provider) {
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	

}
