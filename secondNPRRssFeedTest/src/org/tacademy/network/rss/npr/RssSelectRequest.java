package org.tacademy.network.rss.npr;

import org.tacademy.network.rss.database.DBScheme;
import org.tacademy.network.rss.database.SelectDataRequest;

import android.database.Cursor;

public class RssSelectRequest extends SelectDataRequest {
	RssNews rssNews = new RssNews();
	public RssSelectRequest() {
		this.columns = new String[] {
			DBScheme.ItemTable.ID , 
			DBScheme.ItemTable.TITLE, 
			DBScheme.ItemTable.DESCRIPTION, 
			DBScheme.ItemTable.LINK,
			DBScheme.ItemTable.PUBLISH_DATE, 
			DBScheme.ItemTable.CONTENT	
		};
		this.table = DBScheme.ItemTable.TABLE_NAME;
	}
	
	public boolean setSearchKeyword(String keyword) {
		
		this.selection = "content like '%" + keyword + "%'";
		
		return true;
	}
	@Override
	protected void processCursor(Cursor mData) {
		SingleNewsItem item;
		while(mData.moveToNext()) {
			item = new SingleNewsItem();
			item.recordId = mData.getInt(mData.getColumnIndex(DBScheme.ItemTable.ID));
			item.title = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.TITLE));
			item.description = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.DESCRIPTION));
			item.link = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.LINK));
			item.pubDate = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.PUBLISH_DATE));
			item.content = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.CONTENT));
			rssNews.items.add(item);
		}
		result = rssNews;
	}
	
	
}
