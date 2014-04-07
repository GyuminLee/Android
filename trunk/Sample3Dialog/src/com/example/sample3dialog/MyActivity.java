package com.example.sample3dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MyActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.my_activity);
        getWindow().setTitle("This is just a test");

        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                android.R.drawable.ic_dialog_alert);
        
	}

}
