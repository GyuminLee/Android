package org.tacadmey.basic.mediaplayer;

import android.media.MediaPlayer;
import android.os.Handler;

public class PlayTrackingThread extends Thread {

	private boolean isRunning = false;
	private boolean isTracking = false;
	private boolean isEventNoti = false;
	private int position;
	
	private MediaPlayer mediaPlayer;
	private Handler mainHandler;
	
	public interface OnWatermarkCompletedListener {
		public void onWatermarkCompleted(int position);
	};
	
	OnWatermarkCompletedListener mListener;
	
	public void setOnWatermarkCompletedListener(OnWatermarkCompletedListener listener) {
		mListener = listener;
	}
	
	public PlayTrackingThread(Handler handler,MediaPlayer mediaPlayer) {
		mainHandler = handler;
		this.mediaPlayer = mediaPlayer;
	}

	@Override
	public void run() {

		isRunning = true;
		while(isRunning) {
			try {
				this.sleep(200);
				if (isTracking && isEventNoti) {
					if (mediaPlayer != null) {
						int currentPosition = mediaPlayer.getCurrentPosition();
						if (currentPosition > position) {
							isEventNoti = false;
							mainHandler.post(new Runnable(){
	
								public void run() {
									if (mListener != null) {
										mListener.onWatermarkCompleted(position);
									}
								}
								
							});
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopThread() {
		isRunning = false;
	}
	
	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}
	
	public void startTracking() {
		isTracking = true;
	}
	
	public void stopTracking() {
		isTracking = false;
	}
	
	public void setWaterMark(int position) {
		this.position = position;
		isEventNoti = true;
	}
}
