package com.example.samplegsontest;

import java.util.ArrayList;

public class DataObject {

	String title;
	int age;
	float level;
	DataItem[] items;
	ArrayList<DataItem> list;
	
	public DataObject(String title, int age, float level) {
		this.title = title;
		this.age = age;
		this.level = level;
		items = new DataItem[2];
		items[0] = new DataItem();
		items[0].name = "name1";
		items[0].age = 10;
		items[1] = new DataItem();
		items[1].name = "name2";
		items[1].age = 20;
		list = new ArrayList<DataItem>();
		DataItem item = new DataItem();
		item.name = "name3";
		item.age = 30;
		list.add(item);
		item = new DataItem();
		item.name = "name4";
		item.age = 40;
		list.add(item);
	}
}
