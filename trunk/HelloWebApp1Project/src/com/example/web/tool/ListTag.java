package com.example.web.tool;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ListTag extends SimpleTagSupport {

	private char bullet;
	public void setBullet(char bullet) {
		this.bullet = bullet;
	}
	
	public char getBullet() {
		return bullet;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspFragment body = getJspBody();
		body.invoke(null);
		return;
	}
}
