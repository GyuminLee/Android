package com.example.web.tool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class StarLineTag extends SimpleTagSupport 
	implements DynamicAttributes {
	private Map<String,Object> attrs = new HashMap<String,Object>();
	
	@Override
	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		attrs.put(localName, value);
	}

	private String color;
	private int size;
	public void setColor(String color) {
		this.color = color;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	@Override
	public void doTag() throws JspException, IOException {

		String color = (String)attrs.get("color");
		int size = Integer.parseInt((String)attrs.get("size"));
		JspContext context = getJspContext();
		JspWriter out = context.getOut();
		out.println("<FONT color="+color+">");
		for (int i = 0; i < size; i++) {
			out.print("*");
		}
		out.println("</FONT><BR>");
		return;
	}
}
