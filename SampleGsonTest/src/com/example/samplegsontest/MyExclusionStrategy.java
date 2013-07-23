package com.example.samplegsontest;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class MyExclusionStrategy implements ExclusionStrategy {

	Class<?> typeToSkip;
	
	public MyExclusionStrategy(Class<?> typeToSkip) {
		this.typeToSkip = typeToSkip;
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (clazz==typeToSkip);
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		// TODO Auto-generated method stub
		return (f.getAnnotation(SkipAnnotation.class) != null);
	}

}
