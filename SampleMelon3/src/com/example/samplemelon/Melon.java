package com.example.samplemelon;

import com.google.gson.annotations.SerializedName;

public class Melon {
	int menuId;
	int count;
	int page;
	int totalPages;
	String rankDay;
	
	@SerializedName("rankHour")
	String hour;
	Songs songs;
}
