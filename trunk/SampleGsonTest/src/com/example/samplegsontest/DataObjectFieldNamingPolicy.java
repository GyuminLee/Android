package com.example.samplegsontest;

import com.google.gson.annotations.SerializedName;

public class DataObjectFieldNamingPolicy {
	@SerializedName("custom_title") String title;
	int age;
	float level;
}
