package com.magnitude.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.magnitude.libs.PoiAttributes;

public class PoiAddActivity extends Activity {

	EditText nameView, latitudeView, longitudeView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.poi_add);
	    nameView = (EditText)findViewById(R.id.name);
	    latitudeView = (EditText)findViewById(R.id.latitude);
	    longitudeView = (EditText)findViewById(R.id.longitude);
	    
	    Button btn = (Button)findViewById(R.id.add);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<PoiAttributes> poiArray = new ArrayList<PoiAttributes>();
				String name = null;
				Intent i;
				double latitude = 0.0, longitude = 0.0;
				
				name = nameView.getText().toString();
				latitude = Double.parseDouble(latitudeView.getText().toString());
				longitude = Double.parseDouble(longitudeView.getText().toString());
				PoiAttributes poi = new PoiAttributes(name,"com.magnitude.poi",latitude,longitude,0,0);
				poiArray.add(poi);
				
				i = new Intent("com.magnitude.plugin.whereIsMy.END_ACTIVITY");
				i.putExtra("poiArray", poiArray);
				setResult(RESULT_OK,i);
		        
				finish();
				
			}
		});
	}

}
