package com.example.samplerounddrawbitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView iv = (ImageView)findViewById(R.id.imageView1);
		Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.sample_0)).getBitmap();
		Bitmap circleBitmap = ImageHelper.getRoundedCornerBitmap(bm, bm.getHeight() / 2);
		iv.setImageBitmap(circleBitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
