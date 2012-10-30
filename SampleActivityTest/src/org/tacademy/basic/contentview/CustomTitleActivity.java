package org.tacademy.basic.contentview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CustomTitleActivity extends Activity {

	TextView tv;
	int mCount = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	    Button contentButton = new Button(this);
	    setContentView(contentButton);
	    contentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv.setText("custom title... " + mCount++);
			}
		});
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
	    Button btn = (Button)findViewById(R.id.button1);
	    tv = (TextView)findViewById(R.id.textView1);
	    tv.setText("custom title..." + mCount++);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(CustomTitleActivity.this, "Title Button Click", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
