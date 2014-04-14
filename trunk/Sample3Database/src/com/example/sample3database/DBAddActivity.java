package com.example.sample3database;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DBAddActivity extends ActionBarActivity {

	/** Called when the activity is first created. */
	EditText nameView;
	EditText ageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.db_add_activity);
	    nameView = (EditText)findViewById(R.id.editText1);
	    ageView = (EditText)findViewById(R.id.editText2);
	    Button btn = (Button)findViewById(R.id.btnAdd);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = nameView.getText().toString();
				int age = Integer.parseInt(ageView.getText().toString());
				Person p = new Person();
				p.name = name;
				p.age = age;
				DBManager.getInstance().insertPerson(p);
				finish();
			}
		});
	}

}
