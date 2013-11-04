package com.example.hellotemptest;

public class WeatherTime {
	String day;
	Symbol symbol;
	WindDirection windDirection;
	WindSpeed windSpeed;
	Temperature temperature;
	Pressure pressure;
	Humidity humidity;
	Clounds clouds;
	
	@Override
	public String toString() {
		return day + ",max : " + temperature.max + ", min : " + temperature.min;
	}
}
