package com.example.samplegsontest;

import java.lang.reflect.Field;

import com.google.gson.FieldNamingStrategy;

public class MyFieldNamingStrategy implements FieldNamingStrategy {

	@Override
	public String translateName(Field field) {
		// TODO Auto-generated method stub
		String fieldName = field.getName();
		StringBuilder sb = new StringBuilder();
		char c = fieldName.charAt(0);
		char change = c;
		if (Character.isLowerCase(c)) {
			change = Character.toUpperCase(c);
		}
		sb.append(change);
		if (fieldName.length() > 1) {
			sb.append(fieldName.substring(1));
		}
		return sb.toString();
	}

}
