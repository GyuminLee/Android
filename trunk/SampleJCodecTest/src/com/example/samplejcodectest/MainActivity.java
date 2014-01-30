package com.example.samplejcodectest;

import java.io.File;
import java.io.IOException;

import org.jcodec.api.android.SequenceEncoder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	int[] picture = { R.drawable.gallery_photo_1,
					  R.drawable.gallery_photo_2,
					  R.drawable.gallery_photo_3,
					  R.drawable.gallery_photo_4,
					  R.drawable.gallery_photo_5,
					  R.drawable.gallery_photo_6,
					  R.drawable.gallery_photo_7,
					  R.drawable.gallery_photo_8
					};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btnMovie);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						File out = new File(Environment.getExternalStorageDirectory(),"test.mp4");
						try {
							SequenceEncoder encoder = new SequenceEncoder(out);
							for (int i = 0; i < picture.length; i++) {
								Bitmap bm = ((BitmapDrawable)getResources().getDrawable(picture[i])).getBitmap();
								encoder.encodeImage(bm);
							}
							encoder.finish();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
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
