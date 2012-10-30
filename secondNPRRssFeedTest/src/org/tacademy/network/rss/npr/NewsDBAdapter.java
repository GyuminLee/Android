package org.tacademy.network.rss.npr;

import org.tacademy.network.rss.database.DBScheme;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NewsDBAdapter extends BaseAdapter {

	Context mContext;
	Cursor mData;
	public NewsDBAdapter(Context context,Cursor cursor) {
		mContext = context;
		mData = cursor;
	}
	
	public int getCount() {
		return mData.getCount();
	}

	public Object getItem(int position) {
		SingleNewsItem item = new SingleNewsItem();
		
		mData.moveToFirst();
		mData.move(position);
		
		item.recordId = mData.getInt(mData.getColumnIndex(DBScheme.ItemTable.ID));
		item.title = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.TITLE));
		item.description = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.DESCRIPTION));
		item.link = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.LINK));
		item.pubDate = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.PUBLISH_DATE));
		item.content = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.CONTENT));

		return item;
	}

	public long getItemId(int position) {
		
		mData.moveToFirst();
		mData.move(position);
		
		return mData.getInt(mData.getColumnIndex(DBScheme.ItemTable.ID));
	}

	public View getView(int position, View convertView, ViewGroup group) {
		RssItemView view = (RssItemView)convertView;
		if (view == null) {
			view = new RssItemView(mContext);
		}
//		SingleNewsItem item = new SingleNewsItem();
//		
//		mData.moveToFirst();
//		mData.move(position);
//		
//		item.recordId = mData.getInt(mData.getColumnIndex(DBScheme.ItemTable.ID));
//		item.title = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.TITLE));
//		item.description = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.DESCRIPTION));
//		item.link = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.LINK));
//		item.pubDate = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.PUBLISH_DATE));
//		item.content = mData.getString(mData.getColumnIndex(DBScheme.ItemTable.CONTENT));
		
		view.setData((SingleNewsItem)getItem(position));

		return view;
	}
	
}
