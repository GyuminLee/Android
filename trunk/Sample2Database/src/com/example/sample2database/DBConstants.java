package com.example.sample2database;

import android.provider.BaseColumns;

public class DBConstants {
	public static class PersonTable implements BaseColumns {
		public static final String TABLE_NAME = "persontbl";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_AGE = "age";
	}
}
