package org.tacademy.gps.proximityAlert;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SampleProximityAlertActivity extends Activity {
    /** Called when the activity is first created. */
	LocationManager locationManager;
	final static String PROXIMITY_ALERT = "org.tacademy.gps.proximityalert";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				double latitude = 73.147536;
				double longitude = 0.510648;
				// 미터
				float radius = 100f;
				// 만료되지 않음.
				long expiration = -1;
				Intent i = new Intent(PROXIMITY_ALERT);
				PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), -1, i, 0);
				locationManager.addProximityAlert(latitude, longitude, radius, expiration, pi);
			}
		});
        
    }
}