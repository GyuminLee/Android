package org.tacademy.custom.compoundwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyCompoundControl extends LinearLayout {
 
 int icon_res_id;
 int text_res_id;
 int position;
 
 public MyCompoundControl(Context context) {
  this(context,null);
 }

 public MyCompoundControl(Context context, AttributeSet attrs) {
  super(context, attrs);
  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  LinearLayout compoundView = (LinearLayout)inflater.inflate(R.layout.compound_ui_layout, this);
  
  Button btn01 = (Button) compoundView.findViewById(R.id.btn01);

  btn01.setOnClickListener(new View.OnClickListener() {
 
 public void onClick(View v) {
  Toast.makeText(v.getContext(), "버튼 눌림", Toast.LENGTH_SHORT).show();
 }
   });
 }
 
 public boolean setData(int position,int icon_id, int text_id) {
	 this.icon_res_id = icon_id;
	 this.text_res_id = text_id;
	 this.position = position;
	 
	 // imageview textview 값을 설정.
	 
	 return true;
 }
 public int getA() {
	 return 0;
 }
}
