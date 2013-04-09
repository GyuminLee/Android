package com.example.sampledatabasetest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddDBActivity extends Activity {

	EditText nameText;
	EditText ageText;
	MyDatabaseOpenHelper dbHelper;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.add_db_layout);
	    
	    nameText = (EditText)findViewById(R.id.name);
	    ageText = (EditText)findViewById(R.id.age);
	    
	    Button btn = (Button)findViewById(R.id.btnAdd);
	    btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				String sql = "INSERT INTO "+DBConstant.PersonTable.TABLE_NAME
							+"("+DBConstant.PersonTable.NAME+", "
							+ DBConstant.PersonTable.AGE+") VALUES('" + 
								nameText.getText().toString() + "' , " + 
							ageText.getText().toString() + ")";
				db.execSQL(sql);
				db.close();
				nameText.setText("");
				ageText.setText("0");
			}
		});
	    dbHelper = new MyDatabaseOpenHelper(this, null, null, 0);
	}

}
