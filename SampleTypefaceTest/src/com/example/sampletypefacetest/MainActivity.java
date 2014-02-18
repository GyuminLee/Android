package com.example.sampletypefacetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	File fontFile = new File(Environment.getExternalStorageDirectory(),
			"nanumgothic");
	Typeface nanum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!fontFile.exists()) {
			InputStream is = getResources().openRawResource(R.raw.nanumgothic);
			try {
				OutputStream os = new FileOutputStream(fontFile);
				byte[] buffer = new byte[8096];
				int len;
				while ((len = is.read(buffer)) > 0) {
					os.write(buffer, 0, len);
				}
				os.flush();
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nanum = Typeface.createFromFile(fontFile);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View v = super.onCreateView(name, context, attrs);
		if (v == null && name.equals("TextView")) {
			TextView tv = new TextView(context,attrs);
			tv.setTypeface(nanum);
			v = tv;
		}
		return v;
	}
	
}
