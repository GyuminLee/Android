package com.example.web.tool2;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LineTag extends SimpleTagSupport {

	private String color;
	private int size;
	
	public void setColor(String color) {
		this.color= color;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspContext context = getJspContext();
		JspWriter out = context.getOut();
		out.println("<FONT color='"+color+"'>");
		for (int i = 0; i <size; i++) {
			out.print("=");
		}
		out.println("</FONT>");
	}
}
