package com.example.sample2dialogtest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	Handler mHandler = new Handler();
	String[] mData = { "item1", "item2", "item3" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Alert Dialog");
				builder.setMessage("Dialog Message ???");
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(MainActivity.this,
										"Yes pressed", Toast.LENGTH_SHORT)
										.show();
							}
						});
				builder.setNeutralButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(MainActivity.this,
										"Cancel pressed", Toast.LENGTH_SHORT)
										.show();
							}
						});

				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(MainActivity.this, "No pressed",
										Toast.LENGTH_SHORT).show();
							}
						});
				final AlertDialog dialog = builder.create();
				dialog.show();
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						dialog.dismiss();

					}
				}, 2000);
			}
		});

		btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("List Dialog");
				builder.setItems(mData, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this,
								"selected item : " + mData[which],
								Toast.LENGTH_SHORT).show();
					}
				});
				builder.create().show();

			}
		});

		btn = (Button) findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Single Choice");
				// builder.setSingleChoiceItems(mData, 0, new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// Toast.makeText(MainActivity.this, "selected item : " +
				// mData[which], Toast.LENGTH_SHORT).show();
				// }
				// });
				final boolean[] isSelected = new boolean[] { true, false, true };
				builder.setMultiChoiceItems(mData, isSelected,
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								isSelected[which] = isChecked;
							}
						});
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								StringBuilder sb = new StringBuilder();
								for (int i = 0; i < isSelected.length; i++) {
									if (isSelected[i]) {
										sb.append(mData[i] + ",");
									}
								}
								Toast.makeText(MainActivity.this,
										"Selected Item : " + sb.toString(),
										Toast.LENGTH_SHORT).show();
							}
						});
				builder.create().show();
			}
		});
		
		btn = (Button)findViewById(R.id.button4);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("Progress Dialog");
				dialog.setMessage("current...");
				dialog.setMax(100);
				dialog.setProgress(0);
				dialog.show();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						int value = dialog.getProgress();
						value+=5;
						if (value < dialog.getMax()) {
							dialog.setProgress(value);
							mHandler.postDelayed(this, 100);
						} else {
							dialog.dismiss();
						}
					}
				}, 100);
			}
		});
		btn = (Button)findViewById(R.id.button5);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//				dialog.setIcon(R.drawable.ic_launcher);
//				dialog.setTitle("Downloading...");
//				dialog.setMessage("file abc ...");
////				dialog.setCancelable(false);
//				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//					
//					@Override
//					public void onCancel(DialogInterface dialog) {
//						Toast.makeText(MainActivity.this, "dialog canceled", Toast.LENGTH_SHORT).show();
//					}
//				});
//				dialog.show();
//				mHandler.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						dialog.dismiss();
//					}
//				}, 5000);
				DownloadFragment f = new DownloadFragment();
				f.show(getSupportFragmentManager(), "dialog");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
