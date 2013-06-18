package com.example.web.tool;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MaxTag extends SimpleTagSupport {

	private int num1, num2;
	public void setNum1(Integer num1) {
		this.num1 = num1;
	}
	public void setNum2(Integer num2) {
		this.num2 = num2;
	}
	@Override
	public void doTag() throws JspException, IOException {
		JspContext context = getJspContext();
		if (num1 > num2) {
			context.setAttribute("maximum", num1);
		} else {
			context.setAttribute("maximum", num2);
		}
	}
}
