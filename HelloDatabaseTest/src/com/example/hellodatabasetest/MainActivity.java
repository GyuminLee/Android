package com.example.hellodatabasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText et1, et2, et3;
	ListView listView;
	Cursor mCursor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		listView = (ListView)findViewById(R.id.listView1);
		
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DBOpenHelper helper = new DBOpenHelper(MainActivity.this);
				SQLiteDatabase db = helper.getWritableDatabase();

				String message = et1.getText().toString();
				int type = Integer.parseInt(et2.getText().toString());
				String indate = et3.getText().toString();

				ContentValues values = new ContentValues();
				values.put(DBConstant.TableMessage.FIELD_MESSAGE, message);
				values.put(DBConstant.TableMessage.FIELD_TYPE, type);
				values.put(DBConstant.TableMessage.FIELD_DATE, indate);

				db.insert(DBConstant.TableMessage.TABLE_NAME, null, values);

				// String sql = "INSERT INTO "
				// + DBConstant.TableMessage.TABLE_NAME + "("
				// + DBConstant.TableMessage.FIELD_MESSAGE + ","
				// + DBConstant.TableMessage.FIELD_TYPE + ","
				// + DBConstant.TableMessage.FIELD_DATE + ") values('"
				// + message + "'," + type + ",'" + indate + ",);";
				// db.execSQL(sql);
				db.close();
			}
		});

		btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DBOpenHelper helper = new DBOpenHelper(MainActivity.this);
				SQLiteDatabase db = helper.getReadableDatabase();
				String[] projection = { DBConstant.TableMessage._ID,
						DBConstant.TableMessage.FIELD_MESSAGE,
						DBConstant.TableMessage.FIELD_TYPE,
						DBConstant.TableMessage.FIELD_DATE };
				String selection = "type > ? and type < ?";
				String[] selectionArgs = { "3", "5" };
				Cursor c = db.query(DBConstant.TableMessage.TABLE_NAME,
						projection, null, null, null, null, null);
				mCursor = c;
				
				final int messageId = c.getColumnIndex(DBConstant.TableMessage.FIELD_MESSAGE);
				final int typeId = c.getColumnIndex(DBConstant.TableMessage.FIELD_TYPE);
				final int dateId = c.getColumnIndex(DBConstant.TableMessage.FIELD_DATE);
				
				String[] from = { DBConstant.TableMessage.FIELD_MESSAGE,
						DBConstant.TableMessage.FIELD_TYPE,
						DBConstant.TableMessage.FIELD_DATE };
				int[] to = { R.id.messageView, R.id.typeView, R.id.dateView };
				SimpleCursorAdapter aa = new SimpleCursorAdapter(
						MainActivity.this, R.layout.item_layout, c, from, to, 0);
				aa.setViewBinder(new ViewBinder() {

					@Override
					public boolean setViewValue(View view, Cursor c, int columnIndex) {
						if (columnIndex == typeId) {
							int type = c.getInt(columnIndex);
							TextView tv = (TextView)view;
							switch(type) {
							case 0 :
								tv.setText("send");
								break;
							case 1 :
								tv.setText("receive");
								break;
							}
							return true;
						} else if (columnIndex == messageId || columnIndex == dateId) {
							TextView tv = (TextView)view;
							tv.setText(c.getString(columnIndex));
							return true;
						}
						return false;
					}
					
				});
				listView.setAdapter(aa);
				
				// String sql =
				// "SELECT _id,message,type,indate FROM tblMessage WHERE name=? GROUP BY ... HAVING ... ORDER BY ... LIMIT ... ";
				// String[] args = { "ysi" };
				// Cursor c = db.rawQuery(sql, args);
				// int messageId = c.getColumnIndex("message");
				// int typeId = c.getColumnIndex("type");
				// int indateId = c.getColumnIndex("indate");
				// while(c.moveToNext()) {
				// String message = c.getString(messageId);
				// int type = c.getInt(typeId);
				// String indate = c.getString(indateId);
				// }
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
		}
		super.onDestroy();
	}

}
