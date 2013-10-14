package com.example.samplemelon;

public class Song {
	int songId;
	String songName;
	Artists artists;
	int albumId;
	String albumName;
	int currentRank;
	int pastRank;
	int playTime;
	String issueDate;
	String isTitleSong;
	String isHitSong;
	String isAdult;
	String isFree;
	@Override
	public String toString() {
		return songName;
	}
}
