package org.tacademy.gps.criteria;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SampleCriteriaActivity extends Activity {
    /** Called when the activity is first created. */
	LocationManager locationManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		final Criteria criteria = new Criteria();
		// 정확도
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// 전력사용 정도
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 고도 정보
		criteria.setAltitudeRequired(false);
		// 방위 정보
		criteria.setBearingRequired(false);
		// 속도 정보
		criteria.setSpeedRequired(false);
		// 비용
		criteria.setCostAllowed(true);

		Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String provider = locationManager.getBestProvider(criteria, true);
				Toast.makeText(getApplicationContext(), "provider : " + provider, Toast.LENGTH_LONG).show();
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				StringBuilder sb = new StringBuilder();
				List<String> providers = locationManager.getProviders(criteria, true);
				for(String provider : providers) {
					sb.append("provider : ").append(provider).append("\n");
				}
				Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_LONG).show();
			}
		});
    }
}