package org.tacademy.gps.geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SampleGeoCoderActivity extends Activity {
    /** Called when the activity is first created. */
	LocationManager locationManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (Geocoder.isPresent()) {
			        Geocoder gc = new Geocoder(getApplicationContext(),Locale.KOREA);
					String streetAddress = "서울대학교";
					List<Address> addresses;
					try {
						addresses = gc.getFromLocationName(streetAddress, 10);
						//addresses = gc.getFromLocationName(streetAddress, 10, lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRightLongitude);
						StringBuilder sb = new StringBuilder();
						for (Address address : addresses) {
							sb.append("\n").append("country : ").append(address.getCountryName()).append("\n");
							sb.append("postalcode : ").append(address.getPostalCode()).append("\n");
							sb.append("locality : ").append(address.getLocality()).append("\n");
							sb.append("address line : ");
							for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
								sb.append(address.getAddressLine(i));
							}
							sb.append("\n");
							sb.append("Latitude : ").append(address.getLatitude()).append("\n");
							sb.append("Longitude : ").append(address.getLongitude());
						}
						Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_LONG).show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					// Geocoder 구현되어 있지 않음.
				}
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (Geocoder.isPresent()) {
					Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					double lat = location.getLatitude();
					double lng = location.getLongitude();
					Geocoder gc = new Geocoder(getApplicationContext(),Locale.KOREA);
					StringBuilder sb = new StringBuilder();
					try {
						List<Address> addresses = gc.getFromLocation(lat, lng, 1);
						if (addresses.size() > 0) {
							Address address = addresses.get(0);
							sb.append("\n").append("country : ").append(address.getCountryName()).append("\n");
							sb.append("postalcode : ").append(address.getPostalCode()).append("\n");
							sb.append("locality : ").append(address.getLocality()).append("\n");
							sb.append("address line : ");
							for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
								sb.append(address.getAddressLine(i));
							}
							sb.append("\n");
							Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_LONG).show();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					// Geocoder 구현되어 있지 않음.
				}
			}
		});
    }
}