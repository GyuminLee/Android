package org.tacademy.basic.usagestats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SampleUsageStatsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BufferedReader bufferedreader;
				try {
					bufferedreader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("dumpsys usagestats").getInputStream()));
		            String s1 = null;
		            while ((s1 = bufferedreader.readLine()) != null)
		            {
		                Log.i("UsageStat",s1);
		            }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    }
}