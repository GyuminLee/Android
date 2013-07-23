package com.example.samplegsontest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends Activity {

	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.textView2);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				makeJsonFieldNamingStrategy();
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				makeObjectFieldNamingStrategy();
			}
		});
	}

	void makeJson() {
		DataObject obj = new DataObject("title1",39, 1.0f);
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		tv.setText(json);
	}
	
	void makeJsonExtends() {
		DataObject3 obj = new DataObject3("ysi");
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		tv.setText(json);
	}
	
	void makeJsonNull() {
		DataObject obj = new DataObject(null,39, 1.0f);
		Gson gson = new GsonBuilder().serializeNulls().create();
		String json = gson.toJson(obj);
		tv.setText(json);
	}
	
	void makeJsonVersion() {
		DataObjectVersioning obj = new DataObjectVersioning("ysi", 39, 1.0f);
		Gson gson = new GsonBuilder().setVersion(1.0).create();
		String json = gson.toJson(obj);
		tv.setText(json);
	}
	
	void makeJsonExpose() {
		DataObjectExpose obj = new DataObjectExpose();
		obj.title = "title1";
		obj.age = 39;
		obj.level = 1.0f;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = gson.toJson(obj);
		tv.setText(json);
	}
	
	void makeJsonExclusionStrategy() {
		DataObjectExclusionStrategy obj = new DataObjectExclusionStrategy();
		obj.title = "title1";
		obj.age = 39;
		obj.level = 1.0f;
		MyExclusionStrategy strategy = new MyExclusionStrategy(String.class);
		Gson gson = new GsonBuilder().addSerializationExclusionStrategy(strategy).create();
		String json = gson.toJson(obj);
		tv.setText(json);
	}
	
	void makeJsonFieldNamingPolicy() {
		DataObjectFieldNamingPolicy obj = new DataObjectFieldNamingPolicy();
		obj.title = "title1";
		obj.age = 39;
		obj.level = 1.0f;
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		String json = gson.toJson(obj);
		tv.setText(json);
	}
	
	void makeJsonFieldNamingStrategy() {
		DataObject obj = new DataObject("title1",39, 1.0f);
		Gson gson = new GsonBuilder().setFieldNamingStrategy(new MyFieldNamingStrategy()).create();
		String json = gson.toJson(obj);
		tv.setText(json);
	}
	
	void makeObjectFieldNamingStrategy() {
		Gson gson = new GsonBuilder().setFieldNamingStrategy(new MyFieldNamingStrategy()).create();
		String json = new String((String)tv.getText());
		DataObject obj = gson.fromJson(json, DataObject.class);
		Toast.makeText(MainActivity.this, "DataObject title : " + obj.title + ", age : " + obj.age + ", level : " + obj.level, Toast.LENGTH_SHORT).show();		
	}
	
	void makeObjectFieldNamingPolicy() {
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		String json = new String((String)tv.getText());
		DataObjectFieldNamingPolicy obj = gson.fromJson(json, DataObjectFieldNamingPolicy.class);
		Toast.makeText(MainActivity.this, "DataObject title : " + obj.title + ", age : " + obj.age + ", level : " + obj.level, Toast.LENGTH_SHORT).show();		
	}
	
	void makeObject() {
		Gson gson = new Gson();
		String json = new String((String)tv.getText());
		DataObject obj = gson.fromJson(json, DataObject.class);
		Toast.makeText(MainActivity.this, "DataObject title : " + obj.title + ", age : " + obj.age + ", level : " + obj.level, Toast.LENGTH_SHORT).show();		
	}

	void makeObjectNull() {
		Gson gson = new GsonBuilder().serializeNulls().create();
		String json = new String((String)tv.getText());
		DataObject obj = gson.fromJson(json, DataObject.class);
		Toast.makeText(MainActivity.this, "DataObject title : " + obj.title + ", age : " + obj.age + ", level : " + obj.level, Toast.LENGTH_SHORT).show();		
	}
	
	void makeObjectOther() {
		Gson gson = new Gson();
		String json = new String((String)tv.getText());
		DataObjectOther obj = gson.fromJson(json, DataObjectOther.class);
		Toast.makeText(MainActivity.this, "DataObject title : " + obj.title + ", age : " + obj.age + ", level : " + obj.level, Toast.LENGTH_SHORT).show();
	}

	void makeObjectSimple() {
		Gson gson = new Gson();
		String json = new String((String)tv.getText());
		DataObjectSimple obj = gson.fromJson(json, DataObjectSimple.class);
		Toast.makeText(MainActivity.this, "DataObject title : " + obj.title + ", age : " + obj.age + ", level : " + obj.level, Toast.LENGTH_SHORT).show();		
	}
	
	void makeObjectExtends() {
		Gson gson = new Gson();
		String json = new String((String)tv.getText());
		DataObject3 obj = gson.fromJson(json, DataObject3.class);
		Toast.makeText(MainActivity.this, "DataObject title : " + obj.title + ", age : " + obj.age + ", level : " + obj.level, Toast.LENGTH_SHORT).show();
	}
	
	void makeObjectExclusionStrategy() {
		String json = new String((String)tv.getText());
		MyExclusionStrategy strategy = new MyExclusionStrategy(String.class);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(strategy).create();
		DataObjectExclusionStrategy obj = gson.fromJson(json, DataObjectExclusionStrategy.class);
		Toast.makeText(MainActivity.this, "DataObject title : " + obj.title + ", age : " + obj.age + ", level : " + obj.level, Toast.LENGTH_SHORT).show();
	}

	void doSerializer() {
		DataObject2 obj2 = new DataObject2();
		obj2.name = "name1";
		obj2.age = 39;
		obj2.time = new DateTime(System.currentTimeMillis());
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(DataObject2.class, obj2);
		Gson gson = builder.create();
		
		String json = gson.toJson(obj2);
		tv.setText(json);
	}
	
	void doDeserializer() {
		DataObject2 deserializer = new DataObject2();
		Gson gson = new GsonBuilder().registerTypeAdapter(DataObject2.class, deserializer).create();

		String json = new String((String)tv.getText());
		DataObject2 obj = gson.fromJson(json, DataObject2.class);
		Toast.makeText(this, "DataObject title : " + obj.name + ", age : " + obj.age + ", level : " + obj.time, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
