package org.tacademy.basic.nfc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

public class NFCReceiveActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				for (int i = 0; i < rawMsgs.length; i++) {
					NdefMessage msgs = (NdefMessage)rawMsgs[i];
					if (msgs != null) {
						for (int j = 0; j < msgs.getRecords().length; j++) {
							NdefRecord record = msgs.getRecords()[j];
							DataObject object = makeObjectFromByteArray(record.getPayload());
							//Toast.makeText(getApplicationContext(), new String(record.getPayload()), Toast.LENGTH_SHORT).show();
							processObject(object);
						}
					}
				}
				
				
				
			}
		}
	}
	
	private void processObject(DataObject object) {
		// TODO Auto-generated method stub
		// Object Processing...
		Toast.makeText(this, object.text + " received", Toast.LENGTH_SHORT).show();
	}

	public DataObject makeObjectFromByteArray(byte[] data) {
		DataObject object = new DataObject();
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			//object.readObject(ois);
			object = (DataObject)ois.readObject();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

}
