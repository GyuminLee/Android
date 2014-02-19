package com.example.sampleqrmakeapp;

import java.io.IOException;
import java.nio.charset.Charset;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MakeNFCCode extends Activity {

	NfcAdapter mAdapter;
	String message;
	TextView showView;
	CheckBox readOnlyView;
	String mimeType = "application/com.example.sampleqrmakeapp.nfc";

	public static final String PARAM_MESSAGE = "message";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tag_writer);
		showView = (TextView) findViewById(R.id.showMessage);
		readOnlyView = (CheckBox) findViewById(R.id.checkReadOnly);
		message = getIntent().getStringExtra(PARAM_MESSAGE);
		showView.setText(message);
		mAdapter = NfcAdapter.getDefaultAdapter(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = new Intent(this, MakeNFCCode.class)
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		mAdapter.enableForegroundDispatch(this, pi,
				new IntentFilter[] { filter }, null);
		Button btn = (Button) findViewById(R.id.btnWrite);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tag != null) {
					Ndef ndef = Ndef.get(tag);
					try {
						ndef.connect();
						if (ndef.isWritable()) {
							ndef.writeNdefMessage(makeNdefMessage());
							if (readOnlyView.isChecked()) {
								if (ndef.canMakeReadOnly()) {
									ndef.makeReadOnly();
								} else {
									Toast.makeText(MakeNFCCode.this,
											"this tag don't make read only",
											Toast.LENGTH_SHORT).show();
								}
							}
						} else {
							Toast.makeText(MakeNFCCode.this,
									"this tag don't write message",
									Toast.LENGTH_SHORT).show();
						}
						ndef.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		mAdapter.disableForegroundDispatch(this);
	}

	Tag tag;

	@Override
	protected void onNewIntent(Intent intent) {
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		}
	}

	private NdefMessage makeNdefMessage() {
		NdefRecord record = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeType.getBytes(Charset.forName("US-ASCII")), new byte[0],
				message.getBytes());
		NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
		return msg;
	}
}
