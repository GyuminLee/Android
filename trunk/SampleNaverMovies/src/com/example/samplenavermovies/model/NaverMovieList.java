package com.example.samplenavermovies.model;

import java.util.ArrayList;

public class NaverMovieList {
	public String title;
	public String link;
	public String lastBuildDate;
	public int total;
	public int start;
	public int display;
	public ArrayList<NaverMovieItem> items = new ArrayList<NaverMovieItem>();
}
