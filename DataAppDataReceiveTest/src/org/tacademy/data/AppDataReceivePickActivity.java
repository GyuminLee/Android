package org.tacademy.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AppDataReceivePickActivity extends Activity {

	/** Called when the activity is first created. */
	EditText et;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.pick);
	    // TODO Auto-generated method stub
        
        TextView tv = (TextView)findViewById(R.id.TextView01);
        et = (EditText)findViewById(R.id.EditText01);
        Intent i = getIntent();
        String msg = i.getStringExtra("message");
        tv.setText(msg);
/*        
        Bundle bn = i.getExtras();
        if (bn != null) {
       		String msg = bn.getString("message");
       		if (msg != null) {
       			tv.setText(msg);
       		}
        }
*/        
        
        Button btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("result", et.getText().toString());
				setResult(RESULT_OK,i);
				finish();
			}
		});
        
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

}
