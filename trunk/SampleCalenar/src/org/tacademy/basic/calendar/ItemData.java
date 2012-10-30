package org.tacademy.basic.calendar;

public class ItemData implements CalendarManager.CalendarComparable<ItemData>{
	public int year;
	public int month;
	public int day;
	public String text;
	
	public ItemData(int year,int month,int day,String text) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.text = text;
	}
	
	@Override
	public int compareDate(int year, int month, int day) {
		// TODO Auto-generated method stub
		return ((this.year - year) * 12 + (this.month - month)) * 31 + this.day - day;
	}
	
	@Override
	public int compareToUsingCalendar(ItemData another) {
		// TODO Auto-generated method stub
		return ((this.year - another.year) * 12 + (this.month - another.month)) * 31 + this.day - another.day;
	}
	
}
