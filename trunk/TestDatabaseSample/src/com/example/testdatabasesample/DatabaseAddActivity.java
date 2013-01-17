package com.example.testdatabasesample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DatabaseAddActivity extends Activity {

	EditText nameView;
	EditText phoneView;
	EditText addrView;
	EditText ageView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.database_add_layout);
	    
	    nameView = (EditText)findViewById(R.id.editText1);
	    phoneView = (EditText)findViewById(R.id.editText2);
	    addrView = (EditText)findViewById(R.id.editText3);
	    ageView = (EditText)findViewById(R.id.editText4);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				MyData data = new MyData();
				data.name = nameView.getText().toString();
				data.phone = phoneView.getText().toString();
				data.address = addrView.getText().toString();
				data.age = Integer.parseInt(ageView.getText().toString());
				
				DatabaseModel.getInstance().saveData(data);
				
			}
		});
	    
	}

}
