package com.example.sample3alarm;

public class AlarmDBManager {
	private static AlarmDBManager instance;
	public static AlarmDBManager getInstance() {
		if (instance == null) {
			instance = new AlarmDBManager();
		}
		return instance;
	}
	private AlarmDBManager() {
		
	}
	
	public long getFirstAlarmTime() {
		return System.currentTimeMillis() + 5000;
	}
	
	public void actionAlarm() {
		long time = System.currentTimeMillis();
		// ...
	}
}
