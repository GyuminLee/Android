package com.example.sample3multiitemlist;

public class MyData {
	public static final int TYPE_LEFT = 0;
	public static final int TYPE_RIGHT = 1;
	public static final int TYPE_CENTER = 2;
	public int resId;
	public String content;
	public int type;
	
	public MyData() {
		
	}
	
	public MyData(int resId, String content, int type) {
		this.resId = resId;
		this.content = content;
		this.type = type;
	}
	
}
