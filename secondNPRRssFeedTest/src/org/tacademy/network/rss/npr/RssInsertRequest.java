package org.tacademy.network.rss.npr;

import org.tacademy.network.rss.database.DBScheme;
import org.tacademy.network.rss.database.InsertDataRequest;

import android.content.ContentValues;

public class RssInsertRequest extends InsertDataRequest {
	public RssInsertRequest(SingleNewsItem item) {

		table = DBScheme.ItemTable.TABLE_NAME;

		values = new ContentValues();
		
		values.put(DBScheme.ItemTable.TITLE, item.title);
		values.put(DBScheme.ItemTable.DESCRIPTION, item.description);
		values.put(DBScheme.ItemTable.LINK, item.link);
		values.put(DBScheme.ItemTable.PUBLISH_DATE, item.pubDate);
		values.put(DBScheme.ItemTable.CONTENT, item.content);
		values.put(DBScheme.ItemTable.CHANNEL_ID, 1);
	}
}
