package org.tacademy.basic.nfc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.Toast;

public class NFCSendActivity extends Activity {

	NfcAdapter mAdapter = null;
	NdefMessage mMessage;
	String mimeType = "application/org.tacademy.basic.nfc";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    mAdapter = NfcAdapter.getDefaultAdapter(this);
	    if (mAdapter == null) {
	    	Toast.makeText(this, "NFC not support!!!", Toast.LENGTH_SHORT).show();
	    	finish();
	    }
	    
	    DataObject object = new DataObject();
	    object.id = 10;
	    object.text = "text";
	    
	    object.buffer = "test".getBytes();
	    // TODO Auto-generated method stub
	    
	    mMessage = makeNdefMessage(object);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        if (mAdapter != null) mAdapter.enableForegroundNdefPush(this, mMessage);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
        if (mAdapter != null) mAdapter.disableForegroundNdefPush(this);
	}

	private NdefMessage makeNdefMessage(DataObject object) {
		DataObject[] array = new DataObject[] { object };
		return makeNdefMessage(array);
	}
	
	private NdefMessage makeNdefMessage(DataObject[] objectArray) {
		int count = 0;
		NdefRecord[] recordArray = null;
		NdefRecord[] array = new NdefRecord[objectArray.length];
		for (int i = 0; i < objectArray.length; i++) {
			 NdefRecord record = makeNdefRecord(objectArray[i]);
			 if (record != null) {
				 array[count++] = record;
			 }
		}
		
		if (count < objectArray.length) {
			if (count > 0) {
				recordArray = new NdefRecord[count];
				for (int j = 0;j < count;j++) {
					recordArray[j] = array[j];
				}
			}
		} else {
			recordArray = array;
		}
		
		if (recordArray != null) {
			return new NdefMessage(recordArray);
		}
		return null;
	}

	private NdefRecord makeNdefRecord(DataObject dataObject) {
		// TODO Auto-generated method stub
		NdefRecord record = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			//dataObject.writeObject(oos);
			oos.writeObject(dataObject);
			byte[] payload = baos.toByteArray();
			//byte[] payload = "playload data".getBytes();
			if (payload != null && payload.length > 0) {
				record = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeType.getBytes(Charset.forName("US-ASCII")),new byte[0],payload );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

}
