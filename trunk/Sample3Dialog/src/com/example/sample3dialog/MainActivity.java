package com.example.sample3dialog;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			Button btn = (Button)rootView.findViewById(R.id.btnShowDialog);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("Dialog");
					builder.setMessage("Show Alert Dialog?");
					builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(getActivity(), "Yes Clicked", Toast.LENGTH_SHORT).show();
						}
					});
					builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(getActivity(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
						}
					});
					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(getActivity(), "No Clicked", Toast.LENGTH_SHORT).show();
						}
					});
					
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnSingleChoice);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("Single Choice");
					final CharSequence[] list = {"item1", "item2", "item3"};
					builder.setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(getActivity(), "choice item : " + list[which], Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}
					});
					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
						
						@Override
						public void onCancel(DialogInterface dialog) {
							Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_SHORT).show();
						}
					});
					builder.setCancelable(false);
					builder.create().show();
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnMultiChoice);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("Multi choice");
					final CharSequence[] list = {"item1", "item2" , "item3"};
					final boolean[] selected = {false, true, true};
					builder.setMultiChoiceItems(list, selected, new DialogInterface.OnMultiChoiceClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							selected[which] = isChecked;
						}
					});
					
					builder.setPositiveButton("Completed", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							StringBuilder sb = new StringBuilder();
							for(int i = 0; i < selected.length; i++) {
								if (selected[i]) {
									sb.append(list[i]);
									sb.append(",");
								}
							}
							
							Toast.makeText(getActivity(), "selected : " + sb.toString(), Toast.LENGTH_SHORT).show();
						}
					});
					
					builder.create().show();
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnShowProgress);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ProgressDialog dialog = new ProgressDialog(getActivity());
					dialog.setIcon(R.drawable.ic_launcher);
					dialog.setTitle("Progress...");
					dialog.setMessage("Pleaze wait...");
					dialog.show();	
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnShowHoProgress);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ProgressDialog dialog = new ProgressDialog(getActivity());
					dialog.setIcon(R.drawable.ic_launcher);
					dialog.setTitle("H Progress");
					dialog.setMessage("download...");
					dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					dialog.setMax(1000);
					dialog.setProgress(200);
					dialog.setSecondaryProgress(100);
					dialog.show();
				}
			});
			return rootView;
		}
		
	}

	
}
