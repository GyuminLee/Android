package org.tacademy.basic.contentview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RunOnUIThreadActivity extends Activity {

	TextView mTextView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    mTextView = new TextView(this);
	    mTextView.setText("count : 0");
	    setContentView(mTextView);
	    new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 10; i++) {
					final int count = i;
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mTextView.setText("count : " + count);
						}
						
					});
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mTextView.setText("count end");
					}
					
				});
			}
	    	
	    }).start();
	    
	}

}
