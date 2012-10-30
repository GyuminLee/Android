package org.tacadmey.basic.mediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SampleMediaPlayer2Activity extends Activity {
    /** Called when the activity is first created. */
	MediaPlayer mediaPlayer;
	PlayTrackingThread mTracking;
	Handler mHandler = new Handler();
	int currentPosition = 0;
	int watermark[] = {10000,20000,30000,40000,50000,60000,70000,80000,90000};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.play);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				mediaPlayer.start();
				mTracking.startTracking();
			}
		});
        
        btn = (Button)findViewById(R.id.pause);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				mediaPlayer.pause();
				mTracking.stopTracking();
			}
		});
        
        btn = (Button)findViewById(R.id.Forward);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				int position = mediaPlayer.getCurrentPosition();
				int duration = mediaPlayer.getDuration();
				if (duration > position + 10 * 1000) {
					position += 10 * 1000;
					currentPosition++;
					setWatermark();
					mediaPlayer.seekTo(position);
				} else {
					Toast.makeText(getApplicationContext(), "남은시간 10초 미만", Toast.LENGTH_LONG).show();
				}
			}
		});
        
        btn = (Button)findViewById(R.id.Backward);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				int position = mediaPlayer.getCurrentPosition();
				if (position < 10 * 1000) {
					position = 0;
					currentPosition = 0;
				} else {
					position -= (10 * 1000);
					currentPosition--;
				}
				setWatermark();
				mediaPlayer.seekTo(position);
			}
		});
		createPlayer();
        
    }
    
    public void setWatermark() {
		if (currentPosition < watermark.length) {
			mTracking.setWaterMark(watermark[currentPosition]);    	
		}
    }
    
    public void stopPlayer() {
    	mTracking.setMediaPlayer(null);
    	mTracking.stopThread();
    	mediaPlayer.stop();
    	mediaPlayer.release();
    	mTracking = null;
    	mediaPlayer = null;
    }
    
    public void createPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.winter_blues);

        mTracking = new PlayTrackingThread(mHandler,mediaPlayer);
        mTracking.setOnWatermarkCompletedListener(new PlayTrackingThread.OnWatermarkCompletedListener() {
			
			public void onWatermarkCompleted(int position) {
				// setNextWatermark
				Toast.makeText(getApplicationContext(), "watermark : " + position, Toast.LENGTH_LONG).show();
				currentPosition++;
				if (currentPosition < watermark.length) {
					mTracking.setWaterMark(watermark[currentPosition]);
				}
			}
		});
        currentPosition = 0;
        setWatermark();
        
        mTracking.start();
        
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			public void onCompletion(MediaPlayer mp) {
			
			}
		});
        
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			
			public boolean onError(MediaPlayer mp, int what, int extra) {
				return false;
			}
		});

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
			public void onPrepared(MediaPlayer mp) {
			}
		});

        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
			
			public void onSeekComplete(MediaPlayer mp) {
			
			}
		});    	
    }
}