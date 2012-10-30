package org.tacademy.basic.resource;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ShowLinearActivity extends Activity {

	ClipDrawable clipping;
	AnimationDrawable animation;
	int mClipPosition = 0;
	Handler mHandler = new Handler();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.linear_layout_test);
	    // TODO Auto-generated method stub
	    ImageView iv = (ImageView)findViewById(R.id.imageView2);
	    clipping = (ClipDrawable)iv.getDrawable();
	    iv = (ImageView)findViewById(R.id.imageView1);
	    animation = (AnimationDrawable)iv.getDrawable();
	    
	    Button btn = (Button)findViewById(R.id.button3);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				animation.start();
			}
		});
	    btn = (Button)findViewById(R.id.button5);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				animation.stop();
			}
		});
	    btn = (Button)findViewById(R.id.button4);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mClipPosition = 0;
				postAnimation();
			}
		});
	    
	    ImageButton ib = (ImageButton)findViewById(R.id.imageButton1);
	    ib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageButton ib = (ImageButton)v;
				TransitionDrawable td = (TransitionDrawable)ib.getDrawable();
				td.startTransition(500);
			}
		});
	    
	}

	public void postAnimation() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				runClip();
			}
			
		}, 200);		
	}
	public void runClip() {
		mClipPosition += 1000;
		clipping.setLevel(mClipPosition);
		
		if (mClipPosition < 10000) {
			postAnimation();
		} else {
			mClipPosition = 0;
		}
	}
}
