package org.tacademy.basic.thread;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FrameAnimationActivity extends Activity {
	AnimationDrawable mframeAnimation = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAnimation();
			}
		});
        
        btn = (Button)findViewById(R.id.Button02);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopAnimation();
			}
		});
        
    }
    
    private void startAnimation() {
    	
 /*   	
    	BitmapDrawable frame1 = (BitmapDrawable)getResources().getDrawable(R.drawable.ball1);
    	BitmapDrawable frame2 = (BitmapDrawable)getResources().getDrawable(R.drawable.ball2);
    	BitmapDrawable frame3 = (BitmapDrawable)getResources().getDrawable(R.drawable.ball3);
    	BitmapDrawable frame4 = (BitmapDrawable)getResources().getDrawable(R.drawable.ball4);
    	BitmapDrawable frame5 = (BitmapDrawable)getResources().getDrawable(R.drawable.ball5);
    	BitmapDrawable frame6 = (BitmapDrawable)getResources().getDrawable(R.drawable.ball6);
    	
    	int reasonableDuration = 200;
    	mframeAnimation = new AnimationDrawable();
    	mframeAnimation.setOneShot(false);
    	mframeAnimation.addFrame(frame1, reasonableDuration);
    	mframeAnimation.addFrame(frame2, reasonableDuration);
    	mframeAnimation.addFrame(frame3, reasonableDuration);
    	mframeAnimation.addFrame(frame4, reasonableDuration);
    	mframeAnimation.addFrame(frame5, reasonableDuration);
    	mframeAnimation.addFrame(frame6, reasonableDuration);
    	
    	img.setBackgroundDrawable(mframeAnimation);
    	mframeAnimation.setVisible(true,true);
    	mframeAnimation.start();
*/
    	ImageView img = (ImageView)findViewById(R.id.ImageView01);
    	mframeAnimation = (AnimationDrawable)img.getBackground();
    	mframeAnimation.start();
    }
    
    private void stopAnimation() {
    	ImageView img = (ImageView)findViewById(R.id.ImageView01);
    	mframeAnimation = (AnimationDrawable)img.getBackground();
    	mframeAnimation.stop();
    	//mframeAnimation.setVisible(false, false);
    }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if (hasFocus == true) {
	    	ImageView img = (ImageView)findViewById(R.id.ImageView01);
	    	mframeAnimation = (AnimationDrawable)img.getBackground();
	    	mframeAnimation.start();
		}
		super.onWindowFocusChanged(hasFocus);
	}
}