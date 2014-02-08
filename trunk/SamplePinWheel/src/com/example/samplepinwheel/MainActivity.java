package com.example.samplepinwheel;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	PinWheelView pinWheelView;
	EditText rpsView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pinWheelView = (PinWheelView)findViewById(R.id.pinwheel);
		pinWheelView.setBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.comass)).getBitmap());
		rpsView = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pinWheelView.startPinWheel();
			}
		});
		
		btn = (Button)findViewById(R.id.btnPause);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pinWheelView.pausePinWheel();
			}
		});
		
		btn = (Button)findViewById(R.id.btnInit);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pinWheelView.initializePinWheel();
			}
		});
		
		btn = (Button)findViewById(R.id.btnRps);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				float rps = Float.parseFloat(rpsView.getText().toString());
				pinWheelView.setRPS(rps);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
