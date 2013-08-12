package com.example.samplelocationmanager;

import java.util.ArrayList;

import android.location.Address;

public class SaveAddress {
	private static SaveAddress instance;
	public static SaveAddress getInstance() {
		if (instance == null) {
			instance = new SaveAddress();
		}
		return instance;
	}
	
	private ArrayList<Address> mMemory = new ArrayList<Address>();
	private SaveAddress() {
		
	}
	
	public void add(Address address) {
		mMemory.add(address);
	}
	
	public ArrayList<Address> getList() {
		return mMemory;
	}
	
	public void remove(Address address) {
		mMemory.remove(address);
	}
}
