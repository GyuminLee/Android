package org.tacademy.network.rss.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

public class DataRequest {
	protected Object result;
	public static final int PROCESS_SUCCESS = 1;
	public static final int PROCESS_FAIL = 2;
	
	public interface DataProcessedListener {
		void OnDataProcessed(int result, DataRequest request);
	}
	
	protected DataProcessedListener listener;
	
	public void setDataProcessedListener(DataProcessedListener listener) {
		this.listener = listener;
	}
	
	public boolean isWritable() {
		return false;
	}
	
	public boolean process(SQLiteDatabase db, Handler handler) {
		
		executeSQL(db);
		
		handler.post(new Runnable() {

			public void run() {
				if (listener != null) {
					listener.OnDataProcessed(PROCESS_SUCCESS, DataRequest.this);
				}
			}
			
		});
		return true;
	}
	
	protected void executeSQL(SQLiteDatabase db) {
		// 여기서 Database를 실행함.
	}
	
	public Object getResult() {
		return result;
	}
}
