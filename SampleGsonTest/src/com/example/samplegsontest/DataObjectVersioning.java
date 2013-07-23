package com.example.samplegsontest;

import com.google.gson.annotations.Since;

public class DataObjectVersioning {

	@Since(1.1) String title;
	@Since(1.0) int age;
	float level;
	
	public DataObjectVersioning(String title, int age, float level) {
		this.title = title;
		this.age = age;
		this.level = level;
	}
}
