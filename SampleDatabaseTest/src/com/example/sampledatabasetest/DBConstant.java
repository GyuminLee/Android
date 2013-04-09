package com.example.sampledatabasetest;

import android.provider.BaseColumns;

public class DBConstant {

	public static class PersonTable {
		public static final String TABLE_NAME = "persontbl";
		public static final String ID = BaseColumns._ID;
		public static final String NAME = "name";
		public static final String AGE = "age";
	}
}
