package com.example.samplemelon;

public class Song {
	int songId;
	String songName;
	Artists artists;
	int albumId;
	String albumName;
	int currentRank;
	
	@Override
	public String toString() {
		return songName;
	}
}
