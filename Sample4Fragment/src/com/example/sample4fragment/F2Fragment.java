package com.example.sample4fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class F2Fragment extends Fragment {

	public static final String PARAM_MESSAGE = "message";
	
	EditText editView;
	String editValue = "";
	private static final String TAG = "F2Fragment";
	TextView messageView;
	String message = "";
	
	
	public interface OnReceiveMessageListener {
		public void onReceiveMessage(String message);
	}
	
	OnReceiveMessageListener mListener;
	
	public void setOnReceiveMessageListener(OnReceiveMessageListener listener) {
		mListener = listener;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		if (b != null) {
			message = b.getString(PARAM_MESSAGE);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f2_layout, container, false);
		messageView = (TextView)v.findViewById(R.id.textView1);
		messageView.setText(message);
		editView = (EditText)v.findViewById(R.id.editText1);
		editView.setText(editValue);
		Button btn = (Button)v.findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = editView.getText().toString();
				if (mListener != null) {
					mListener.onReceiveMessage(text);
				}
			}
		});
		return v;
	}
	
	public void log() {
		Log.i(TAG,"F2 log");
	}
	
	@Override
	public void onDestroyView() {
		editValue = editView.getText().toString();
		super.onDestroyView();
	}
	
	@Override
	public void onResume() {
		getActivity().setTitle("F2Fragment");
		super.onResume();
	}
}
