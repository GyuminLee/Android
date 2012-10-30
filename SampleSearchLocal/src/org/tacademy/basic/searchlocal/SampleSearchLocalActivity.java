package org.tacademy.basic.searchlocal;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class SampleSearchLocalActivity extends Activity {
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
				onSearchRequested();
			}
		});
        startSearch("°¡", false, null, false);
    }
    
    @Override
    public boolean onSearchRequested() {
    	// TODO Auto-generated method stub
        startSearch("³ª", false, null, false);
    	return true;
    }
}