package org.tacademy.network.rss.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SelectDataRequest extends DataRequest {
	public String table;
	public String[] columns;
	public String selection;
	public String[] selectionArgs;
	public String groupBy;
	public String having;
	public String orderBy;
	
	public static final String ORDER_ASCENT = " ASC";
	public static final String ORDER_DESCENT = " DESC";

	@Override
	protected void executeSQL(SQLiteDatabase db) {
		Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		processCursor(c);
	}
	
	protected void processCursor(Cursor c) {
		// cursor 로부터 object를 생성하여 넘겨줌.
		// result = c;
	}
	
	public void setSelection(String selection,String[] selectionArgs) {
		this.selection = selection;
		this.selectionArgs = selectionArgs;
	}
	
	public void setGroupBy(String fieldName) {
		this.groupBy = fieldName;
	}
	
	public void setHaving(String having) {
		this.having = having;
	}
	
	public void setOrderBy(String fieldName, String order) {
		this.orderBy = fieldName + order;
	}
	
	
	
}
