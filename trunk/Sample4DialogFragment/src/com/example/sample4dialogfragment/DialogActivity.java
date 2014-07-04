package com.example.sample4dialogfragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DialogActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.dialog_layout);
	    
	    Button btn = (Button)findViewById(R.id.btn_ok);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(DialogActivity.this, "dialog activity", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	    
	}

}
