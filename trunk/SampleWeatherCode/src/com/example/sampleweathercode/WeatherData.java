package com.example.sampleweathercode;

public class WeatherData {
	public int seq;
	public int hour;
	public int day;
	public float temp;
	public float tmx;
	public float tmn;
	public int sky;
	public int pty;
	public String wfKor;
	public String wfEn;
	public int pop;
	public float r12;
	public float s12;
	public float ws;
	public int wd;
	public String wdKor;
	public String wdEn;
	public int reh;
	public float r06;
	public float s06;
	@Override
	public String toString() {
		return hour + "시 : " + wfKor + ", Temp : " + temp;
	}
}
