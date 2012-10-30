package org.tacademy.basic.contentview;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LaunchModeParentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch_standard);
		Toast.makeText(this, this.getClass().getSimpleName() + " : " + this.toString(), Toast.LENGTH_SHORT).show();
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(LaunchModeParentActivity.this, LaunchModeParentActivity.this.getClass());
				startActivity(i);
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LaunchModeParentActivity.this, LaunchModeBActivity.class);
				
				Intent result = new Intent(LaunchModeParentActivity.this,LaunchModeParentActivity.this.getClass());
				PendingIntent pi = PendingIntent.getActivity(LaunchModeParentActivity.this, 0, result, 0);
				i.putExtra("result", pi);
				startActivityForResult(i,0);
			}
		});
		
		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LaunchModeParentActivity.this, LaunchModeBActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				Intent result = new Intent(LaunchModeParentActivity.this,LaunchModeParentActivity.this.getClass());
				PendingIntent pi = PendingIntent.getActivity(LaunchModeParentActivity.this, 0, result, 0);
				i.putExtra("result", pi);
				startActivityForResult(i,0);
			}
		});
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, this.getClass().getSimpleName() + " called onNewIntent", Toast.LENGTH_SHORT).show();
		super.onNewIntent(intent);
	}
}
