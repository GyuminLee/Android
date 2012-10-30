package org.tacademy.gps.provider;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SampleGpsProviderActivity extends Activity {
    /** Called when the activity is first created. */
	TextView tv;
	LocationManager locationManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        tv = (TextView)findViewById(R.id.textView1);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				StringBuilder sb = new StringBuilder("providers : ");
				List<String> providers = locationManager.getProviders(true);
				for (String provider : providers) {
					locationManager.requestLocationUpdates(provider, 0, 0, new LocationListener() {

						public void onLocationChanged(Location location) {
							Log.i("SampleGpsProvider","lat:"+location.getLatitude() + ",lng:" + location.getLongitude());
						}

						public void onProviderDisabled(String arg0) {
						}

						public void onProviderEnabled(String provider) {
						}

						public void onStatusChanged(String provider, int status, Bundle extras) {
							
						}
						
					});
					
					sb.append("\n").append(provider).append(":");
					Location location = locationManager.getLastKnownLocation(provider);
					if (location != null) {
						double lat = location.getLatitude();
						double lng = location.getLongitude();
						sb.append(lat).append(", ").append(lng);
					} else {
						sb.append("location not found");
					}
					
				}
				tv.setText(sb);
			}
		});
    }
}