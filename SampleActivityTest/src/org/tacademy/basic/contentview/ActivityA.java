package org.tacademy.basic.contentview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityA extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.flag_test);
	    setTitle("ActivityA");
	    // TODO Auto-generated method stub
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ActivityA.this, ActivityB.class);
				startActivity(i);
			}
		});
	}

}
