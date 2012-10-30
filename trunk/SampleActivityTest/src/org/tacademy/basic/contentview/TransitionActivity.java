package org.tacademy.basic.contentview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TransitionActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.finish_test);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
	    // TODO Auto-generated method stub
	}

}
