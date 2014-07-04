package com.example.sample4dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btn_alert_dialog);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher)
				.setTitle("AlertDialog")
				.setMessage("Dialog Test");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Yes Clicked", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
						
					}
				});
				
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "No Clicked", Toast.LENGTH_SHORT).show();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				Toast.makeText(MainActivity.this, "show dialog", Toast.LENGTH_SHORT).show();
			}
			
		});
		
		btn = (Button)findViewById(R.id.btn_single_choice);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Single Dialog");
				builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "choice : " + items[which], Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
//				builder.setCancelable(false);
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						Toast.makeText(MainActivity.this, "dialog cancel", Toast.LENGTH_SHORT).show();
					}
				});
				builder.create().show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_multi_choice);
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Multiple Choice");
				final boolean[] checkedItems = new boolean[items.length];
				checkedItems[0] = true;
				checkedItems[2] = true;
				builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						checkedItems[which] = isChecked;
					}
				});
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0 ; i < checkedItems.length; i++) {
							if (checkedItems[i]) {
								sb.append(items[i] + ",");
							}
						}
						Toast.makeText(MainActivity.this, "items : " + sb.toString(), Toast.LENGTH_SHORT).show();						
					}
				});
				builder.create().show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_progress_indeterminate);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("Progress...");
				dialog.setMessage("downloading...");
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setMax(100);
				dialog.setProgress(50);
				dialog.setSecondaryProgress(70);
				dialog.show();
			}
		});
	}
	
	String[] items = new String[] { "item1" , "item2" , "item3" , "item4"};
}
