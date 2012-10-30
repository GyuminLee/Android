package org.tacademy.network.rss.npr;

public class SingleNewsItem {
	public int recordId = -1;
	public String pubDate;
	public String title;
	public String description;
	public String link;
	public String content;
	
	public String getPubDate() { return pubDate; }
	public String getTitle() { return title; }
	public String getDescription() { return description; }
	public String getLink() { return link; }

	public SingleNewsItem(String _pubDate,String _title, String _description, String _link, String _content) {
		pubDate = _pubDate;
		title = _title;
		description = _description;
		link = _link;
		content = _content;
	}
	
	public SingleNewsItem() {
		
	}
	
	@Override
	public String toString() {
		return title;
	}
}
