package com.example.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConstant {
	public static class BoardTable {
		public static final String ID = "id";
		public static final String AUTHOR = "author";
		public static final String TITLE = "title";
		public static final String CONTENT = "content";
		public static final int FIELD_ID = 1;
		public static final int FIELD_AUTHOR = 1;
		public static final int FIELD_TITLE = 2;
		public static final int FIELD_CONTENT = 3;
		public static final String BOARD_INSERT = "INSERT INTO `authority`.`boardtbl` (`id`, `author`, `title`, `content`) VALUES (NULL, ?, ?, ?);";
		public static final int FIELD_COUNT = 2;
		public static final int FIELD_START = 1;
		public static final String BOARD_SELECT = "SELECT * FROM  `boardtbl` LIMIT ? , ?";
		public static final String BOARD_DETAIL = "SELECT * FROM  `boardtbl` WHERE  `id` = ?";	
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/authority?useUnicode=true&characterEncoding=UTF-8", "root", "apmsetup");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
