package com.example.sampleplayerservice;

import com.example.sampleplayerservice.IPlayerStateChangeListener;

interface IMediaPlayer {
	void play();
	void pause();
	void next();
	void previous();
	int getCurrentPosition();
	int getDuration();
	int getPlayerState();
	void setLooping(boolean isLoop);
	boolean isLooping();
	void seekTo(int position);
	void registerServiceCallback(IPlayerStateChangeListener callback);
	void unregisterServiceCallback(IPlayerStateChangeListener callback);
}