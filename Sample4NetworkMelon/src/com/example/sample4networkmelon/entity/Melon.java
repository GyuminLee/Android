package com.example.sample4networkmelon.entity;

import com.google.gson.annotations.SerializedName;

public class Melon {
	public int menuId;
	
	@SerializedName("count")
	public int length;
	
	public Songs songs;
}
