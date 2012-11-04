package com.magnitude.libs;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class Popup implements OnDismissListener{
	private PopupWindow pop;
	private View layout;
	private int layoutXML;
	private View baseView;
	private Context ctx;
	private int x,y;
	
	public Popup(Activity activity,View baseView,int layout,int height,int width) {
		this.ctx = activity;
		this.layoutXML = layout;
		this.pop = new PopupWindow(ctx);
		this.pop.setOnDismissListener(this);
		this.baseView = baseView;
		this.layout = LayoutInflater.from(ctx).inflate(layoutXML,null);
		this.pop.setWidth(width);
		this.pop.setHeight(height);
		this.pop.setFocusable(true);
		this.pop.update();
		this.pop.setContentView(this.layout);
	}	
	
	public void show()	{
		show(0,0);
	}
	
	public void show(int x, int y) {
		this.x = x;
		this.y = y;
		//pop.showAtLocation(baseView, Gravity.LEFT | Gravity.TOP ,this.x, this.y);
		pop.showAtLocation(baseView, Gravity.LEFT | Gravity.TOP ,this.x, this.y);
	}
	
	public void dismiss() {
		pop.dismiss();
	}
	
	public void onDismiss() {
		//this.layout = LayoutInflater.from(ctx).inflate(layoutXML,null);
		//pop.update();
		//pop.setContentView(this.layout);		
	}
	
	public PopupWindow getPop() {
		return pop;
	}

	public void setPop(PopupWindow pop) {
		this.pop = pop;
	}

	public View getLayout() {
		return layout;
	}

	public void setLayout(View layout) {
		this.layout = layout;
	}

	public int getLayoutXML() {
		return layoutXML;
	}

	public void setLayoutXML(int layoutXML) {
		this.layoutXML = layoutXML;
	}

	public View getBaseView() {
		return baseView;
	}

	public void setBaseView(View baseView) {
		this.baseView = baseView;
	}

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
}