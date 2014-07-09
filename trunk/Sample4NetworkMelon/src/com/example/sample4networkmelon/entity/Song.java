package com.example.sample4networkmelon.entity;

public class Song {
	public int songId;
	public String songName;
	public Artists artists;
	public String albumName;
	public int currentRank;
	public int pastRank;
	public int playTime;
	
	@Override
	public String toString() {
		return songName;
	}
}
