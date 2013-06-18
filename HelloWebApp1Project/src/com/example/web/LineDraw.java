package com.example.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class LineDraw {
	public static void drawLine(JspWriter out) {
		try {
			out.println("====================================================");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
