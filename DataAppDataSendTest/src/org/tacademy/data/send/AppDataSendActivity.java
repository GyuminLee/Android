package org.tacademy.data.send;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AppDataSendActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setAction("android.intent.action.PICK");
				i.setType("vnd.example.greeting/vnd.example.greeting-text");
				i.putExtra("message", "Send Activity");
				startActivityForResult(i,0);
				
			}
		});
        
        btn = (Button)findViewById(R.id.Button02);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setAction(Intent.ACTION_PICK/* "android.intent.action.VIEW"*/);
				i.setType("vnd.example.greeting/vnd.example.greeting-text");
				Bundle bundle = new Bundle();
				bundle.putString("message", "Hello!!!");
				i.putExtras(bundle);
				startActivityForResult(i,0);
			}
		});

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    	if (resultCode == RESULT_OK) {
    		String msg = result.getStringExtra("result");
    		TextView tv = (TextView)findViewById(R.id.TextView01);
    		tv.setText(msg);
    	}
    }
    
}