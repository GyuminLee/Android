package com.example.sample4basicwidget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NewActivity extends Activity {

	ImageView imageView;
	Drawable d;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.test2_main);
	    imageView = (ImageView)findViewById(R.id.imageView1);
		d = imageView.getDrawable();
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int level = d.getLevel();
				level += 2500;
				if (level >= 10000) {
					level -= 10000;
				}
				d.setLevel(level);
//				imageView.setImageResource(R.drawable.chrysanthemum);
			}
		});
	}

}
