package org.tacademy.basic.samplelist2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    Intent i = getIntent();
	    MyData data = (MyData)i.getParcelableExtra("data");
	    
	    // TODO Auto-generated method stub
	}

}
