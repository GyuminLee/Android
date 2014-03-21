package com.example.testnavermovies;

public class MovieItem {
	public String title;
	public String link;
	public String image;
	public String subtitle;
	public String pubDate;
	public String director;
	public String actor;
	public float userRating;
	
	@Override
	public String toString() {
		return title + "\n\r" + subtitle;
	}
}
