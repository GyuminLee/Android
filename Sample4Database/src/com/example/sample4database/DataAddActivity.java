package com.example.sample4database;

import com.example.sample4database.entity.Person;
import com.example.sample4database.manager.DataManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DataAddActivity extends Activity {

	EditText nameView;
	EditText ageView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.add_layout);
	    nameView = (EditText)findViewById(R.id.editText1);
	    ageView = (EditText)findViewById(R.id.editText2);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Person p = new Person();
				p.name = nameView.getText().toString();
				p.age = Integer.parseInt(ageView.getText().toString());
				DataManager.getInstance().addPerson(p);
				nameView.setText("");
				ageView.setText("0");
			}
		});
	}

}
