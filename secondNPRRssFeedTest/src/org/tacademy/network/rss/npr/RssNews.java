package org.tacademy.network.rss.npr;

import java.util.ArrayList;


public class RssNews {
	public String title;
	public String link;
	public String description;
	public String copyright;
	public String generator;
	public String lastBuildDate;
	public RssImage image;
	public ArrayList<SingleNewsItem> items = new ArrayList<SingleNewsItem>();
}
