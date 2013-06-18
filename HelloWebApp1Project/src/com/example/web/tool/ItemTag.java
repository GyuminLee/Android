package com.example.web.tool;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ItemTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		JspTag parent = getParent();
		if ((parent == null) || !(parent instanceof ListTag)) {
			throw new JspException("parent not ListTag");
		}
		JspContext context = getJspContext();
		JspWriter out = context.getOut();
		JspFragment body = getJspBody();
		char bullet = ((ListTag)parent).getBullet();
		out.print(bullet);
		body.invoke(out);
		out.println("<BR>");
		return;
	}
}
