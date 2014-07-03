package com.example.sample4fragment;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import android.os.Bundle;

public class MyActivity extends FragmentActivity {

	F2Fragment f2;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity);
	    f2 = (F2Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment1);
	    f2.log();
	    f2.setOnReceiveMessageListener(new F2Fragment.OnReceiveMessageListener() {
			
			@Override
			public void onReceiveMessage(String message) {
				Toast.makeText(MyActivity.this, "my activity : " + message, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
