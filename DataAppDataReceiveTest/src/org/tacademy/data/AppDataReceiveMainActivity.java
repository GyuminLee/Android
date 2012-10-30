package org.tacademy.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AppDataReceiveMainActivity extends Activity {
    /** Called when the activity is first created. */
	EditText et;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et = (EditText)findViewById(R.id.EditText01);
        Button btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setAction("android.intent.action.PICK");
				i.setType("vnd.example.greeting/vnd.example.greeting-text");
				i.putExtra("message", et.getText().toString());
				startActivityForResult(i,0);
				
			}
		});
        
        btn = (Button)findViewById(R.id.Button02);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),AppDataReceivePickActivity.class);
				//Intent ii = new Intent(AppDataReceivePickActivity.class.getName());
				//i.putExtra("message", et.getText().toString());
				//i.putExtra("name2", 10);
				Bundle bn = new Bundle();
				bn.putString("message", et.getText().toString());
				i.putExtras(bn);
				//startActivity(i);
				startActivityForResult(i,0);
				//i.put
/*				
				Intent i = new Intent();
				i.setAction("android.intent.action.PICK");
				i.setType("vnd.example.greeting/vnd.example.greeting-text");
				Bundle bn = new Bundle();
				bn.putString("message", "Send Activity");
				i.putExtras(bn);
				
				startActivityForResult(i,0);
*/				
				
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
    	if (requestCode == 0) {
    		if (resultCode == RESULT_OK) {
    			String msg = result.getStringExtra("result");
    			TextView tv = (TextView)findViewById(R.id.TextView01);
    			tv.setText(msg);
    		}
    	} else if (requestCode == 1) {
    	} else if (requestCode == 2) {
    	}
    	
    }
}