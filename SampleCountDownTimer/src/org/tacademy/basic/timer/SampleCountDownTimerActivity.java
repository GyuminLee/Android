package org.tacademy.basic.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SampleCountDownTimerActivity extends Activity {
    /** Called when the activity is first created. */
	TextView mTVRemainTime;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTVRemainTime = (TextView)findViewById(R.id.remaintime);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CountDownTimer(30000,1000) {

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						mTVRemainTime.setText("count down end...");
					}

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						mTVRemainTime.setText("remain time " + (millisUntilFinished / 1000));
					}
					
				}.start();
			}
		});
    }
}