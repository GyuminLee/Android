package com.example.shared;

import java.util.ArrayList;

public interface MyWebInterface {
	public double add(double x, double y);
	public void add(MyData data);
	public MyData get(String name);
	public void register(String name, String regId);
	public String[] getUserList();
	public void sendMessage(String name,String message);
}
