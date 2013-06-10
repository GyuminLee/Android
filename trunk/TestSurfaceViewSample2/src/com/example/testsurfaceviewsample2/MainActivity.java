package com.example.testsurfaceviewsample2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity {

	SurfaceView view;

	MyDrawing drawing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = (SurfaceView)findViewById(R.id.surfaceView1);
		drawing = new MyDrawing();
		view.getHolder().addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				drawing.stop();
				drawing.setSurfaceHolder(null);
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				drawing.setSurfaceHolder(holder);
				drawing.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				drawing.setSurfaceHolder(holder);
				drawing.start();
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
