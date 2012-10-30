package org.tacademy.basic.contentview;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FinishTestActivity extends Activity {

	public static final int REQUEST_ACTIVITY = 1;
	Handler mHandler = new Handler();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.finish_test);
	    // TODO Auto-generated method stub
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	    
	    btn = (Button)findViewById(R.id.button2);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(FinishTestActivity.this,AddContentViewActivity.class);
				startActivityForResult(i,REQUEST_ACTIVITY);
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						finishActivity(REQUEST_ACTIVITY);
					}
					
				}, 5000);
			}
		});
	    
	    btn = (Button)findViewById(R.id.button3);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    ComponentName cn = getCallingActivity();
			    String pkg = getCallingPackage();
			    Toast.makeText(FinishTestActivity.this, "class : " + cn.getClassName() + "\nclass package : " + cn.getPackageName() + "\npackage : " + pkg, Toast.LENGTH_SHORT).show();
			    
			    ComponentName currentCN = getComponentName();
			    Toast.makeText(FinishTestActivity.this, "current class : " + currentCN.getClassName() + "\nclass package : " + currentCN.getPackageName(), Toast.LENGTH_SHORT).show();
			    
			    Toast.makeText(FinishTestActivity.this, "class : " + getLocalClassName(), Toast.LENGTH_SHORT).show();
				
			}
		});
	    
	    Toast.makeText(this, "FinishTestActivity task id : " + getTaskId()  + "\nIsTaskRoot : " + isTaskRoot(), Toast.LENGTH_SHORT).show();
	}

}
