package com.example.sample4dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment {

	String[] items = new String[] { "item1" , "item2" , "item3" , "item4" , "item5" , "item6" };
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("Dialog");
		builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				Fragment old = getFragmentManager().findFragmentByTag("dialog");
				if (old != null) {
					ft.remove(old);
				}
				ft.addToBackStack(null);
				My2DialogFragment f = new My2DialogFragment();
				f.show(ft, "dialog");
			}
		});
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
			}
		});
		return builder.show();
	}
}
