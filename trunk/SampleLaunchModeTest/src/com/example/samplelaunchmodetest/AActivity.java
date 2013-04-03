package com.example.samplelaunchmodetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AActivity extends Activity {

	TextView messageView;
	public static final String PARAM_NAME_CALLER_MESSAGE = "message";
	
	int count = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.a_activity_layout);
	    
	    messageView = (TextView)findViewById(R.id.textView2);
	    
	    Intent i = getIntent();
	    String message = i.getStringExtra(PARAM_NAME_CALLER_MESSAGE);
	    messageView.setText("onCreate : caller message - " + message + "\n"
	    		+ "activity " + this.toString());
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AActivity.this, AActivity.class);
				intent.putExtra(PARAM_NAME_CALLER_MESSAGE, "AActivity call " + count);
				count++;
				startActivity(intent);
			}
		});
	    
	    btn =(Button)findViewById(R.id.button2);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		String message = intent.getStringExtra(PARAM_NAME_CALLER_MESSAGE);
		
		messageView.setText("onNewIntent : caller message - " + message + "\n"
				+ "activity " + this.toString());
	}

}
