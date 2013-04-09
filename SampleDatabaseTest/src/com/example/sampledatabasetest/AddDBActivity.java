package com.example.sampledatabasetest;

import com.example.sampledatabasetest.manager.DBConstant;
import com.example.sampledatabasetest.manager.DBManager;
import com.example.sampledatabasetest.manager.MyDatabaseOpenHelper;
import com.example.sampledatabasetest.manager.Person;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddDBActivity extends Activity {

	EditText nameText;
	EditText ageText;
	
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
//				String sql = "INSERT INTO "+DBConstant.PersonTable.TABLE_NAME
//							+"("+DBConstant.PersonTable.NAME+", "
//							+ DBConstant.PersonTable.AGE+") VALUES('" + 
//								nameText.getText().toString() + "' , " + 
//							ageText.getText().toString() + ")";
//				db.execSQL(sql);

				Person p = new Person();
				p.name = nameText.getText().toString();
				p.age = Integer.parseInt(ageText.getText().toString());
				
				DBManager.getInstance().insertPerson(p);
				
				nameText.setText("");
				ageText.setText("0");
			}
		});
	}

}
