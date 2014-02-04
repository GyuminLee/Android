package com.example.sampleaubio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						InputStream is = getResources().openRawResource(R.raw.test);
						File path = new File(Environment.getExternalStorageDirectory(), "test.wav");
						try {
							FileOutputStream out = new FileOutputStream(path);
							byte[] buffer = new byte[8096];
							int len = 0;
							while ((len = is.read(buffer)) >= 0) {
								out.write(buffer,0,len);
							}
							out.flush();
							out.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						final int tempo = TempoDetector.detectTempo(path.getAbsolutePath());
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(MainActivity.this, "tempo : " + tempo, Toast.LENGTH_SHORT).show();
							}
						});
						
					}
				}).start();
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
