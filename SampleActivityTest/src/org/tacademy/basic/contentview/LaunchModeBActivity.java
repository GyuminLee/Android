package org.tacademy.basic.contentview;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchModeBActivity extends Activity {

	/** Called when the activity is first created. */
	PendingIntent pi;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    setContentView(R.layout.launch_callee);
	    Intent i = getIntent();
	    pi = i.getParcelableExtra("result");
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					pi.send();
				} catch (CanceledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	    
	}

}
