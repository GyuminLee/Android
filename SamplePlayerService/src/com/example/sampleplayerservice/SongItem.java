package com.example.sampleplayerservice;

import android.net.Uri;

public class SongItem {
	long id;
	Uri uri;
	String title;
	String displayName;
	String artist;
	String album;
	@Override
	public String toString() {
		return displayName + "\n\r" + title + "\n\r" + artist + ","+ album;
	}
}
