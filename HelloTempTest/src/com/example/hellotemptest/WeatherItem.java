package com.example.hellotemptest;

public class WeatherItem {
	int dt;
	ItemTemp temp;
	float pressure;
	int humidity;
	ItemWeather weather;
	float speed;
	int deg;
	int clouds;
	@Override
	public String toString() {
		return "max : " + temp.max + ", min : " + temp.min;
	}
}
