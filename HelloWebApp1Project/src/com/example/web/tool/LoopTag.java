package com.example.web.tool;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LoopTag extends SimpleTagSupport {
	private int start, end;
	public void setStart(Integer start) {
		this.start = start;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	private String var;
	public void setVar(String var) {
		this.var = var;
	}
	@Override
	public void doTag() throws JspException, IOException {
		JspContext context = getJspContext();
		JspWriter out = context.getOut();
		JspFragment body = getJspBody();
		for (int i = start; i <= end; i++) {
			context.setAttribute(var, i);
			body.invoke(out);
		}
	}
}
