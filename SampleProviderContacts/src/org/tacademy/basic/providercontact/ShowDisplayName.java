package org.tacademy.basic.providercontact;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowDisplayName extends ListActivity {

	ArrayList<ContactsData> items;
	ListView list;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    list = getListView();
	    setProgressBarIndeterminateVisibility(true);
	    // TODO Auto-generated method stub
	    new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
			    ContentResolver cr = getContentResolver();
			    Cursor c = cr.query(Contacts.CONTENT_URI, null, null, null, Contacts.DISPLAY_NAME);
			    items = makeContactsData(c);
			    c.close();
			    runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
					    ArrayAdapter<ContactsData> adapter = new ArrayAdapter<ContactsData>(ShowDisplayName.this,android.R.layout.simple_list_item_1,items);
					    setListAdapter(adapter);
					    setProgressBarIndeterminateVisibility(false);
					}
			    	
			    });
				
			}
	    	
	    }).start();
	    
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "vCard : " + items.get(position).vCardData, Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this,ShowDetails.class);
		Bundle b = new Bundle();
		b.putSerializable("item", items.get(position));
		i.putExtras(b);
		startActivity(i);
	}
	
	private ArrayList<ContactsData> makeContactsData(Cursor c) {
		// TODO Auto-generated method stub
		ArrayList<ContactsData> list = new ArrayList<ContactsData>();
		if (c.moveToFirst()) {
			do {
				ContactsData data = new ContactsData();
				data.rowId = c.getLong(c.getColumnIndex(Contacts._ID));
				data.customRingtone = c.getString(c.getColumnIndex(Contacts.CUSTOM_RINGTONE));
				data.displayName = c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME));
				data.hasPhoneNumber = (c.getInt(c.getColumnIndex(Contacts.HAS_PHONE_NUMBER)) == 1)?true:false;
				data.isSendToVoiceMail = (c.getInt(c.getColumnIndex(Contacts.SEND_TO_VOICEMAIL)) == 1)?true:false;
				data.isStarred = (c.getInt(c.getColumnIndex(Contacts.STARRED)) == 1)? true:false;
				data.isInVisibleGroup = (c.getInt(c.getColumnIndex(Contacts.IN_VISIBLE_GROUP))==1) ? true : false;
				data.lastTimeContacted = c.getLong(c.getColumnIndex(Contacts.LAST_TIME_CONTACTED));
				data.lookupKey = c.getString(c.getColumnIndex(Contacts.LOOKUP_KEY));
				data.photoId = c.getLong(c.getColumnIndex(Contacts.PHOTO_ID));
				data.presence = c.getInt(c.getColumnIndex(Contacts.CONTACT_PRESENCE));
				data.status = c.getString(c.getColumnIndex(Contacts.CONTACT_STATUS));
				data.statusLabel = c.getLong(c.getColumnIndex(Contacts.CONTACT_STATUS_LABEL));
				data.statusIcon = c.getLong(c.getColumnIndex(Contacts.CONTACT_STATUS_ICON));
				data.statusResPackage = c.getString(c.getColumnIndex(Contacts.CONTACT_STATUS_RES_PACKAGE));
				data.statusTimestamp = c.getLong(c.getColumnIndex(Contacts.CONTACT_STATUS_TIMESTAMP));
				data.timesContacted = c.getInt(c.getColumnIndex(Contacts.TIMES_CONTACTED));
				
				Uri uri = Uri.withAppendedPath(Contacts.CONTENT_VCARD_URI, data.lookupKey);
				AssetFileDescriptor fd;
				try {
					fd = getContentResolver().openAssetFileDescriptor(uri, "r");
					FileInputStream fis = fd.createInputStream();
					byte[] b = new byte[(int)fd.getDeclaredLength()];
					fis.read(b);
					data.vCardData = new String(b);
					fd.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				list.add(data);
			} while(c.moveToNext());
		}
		return list;
	}

	public static class ContactsData implements Serializable {

		static final long serialVersionUID = 10275539472837495L;
		
		long rowId;
		String lookupKey;
		String displayName;
		long photoId;
		int presence;
		String status;
		long statusTimestamp;
		String statusResPackage;
		long statusLabel;
		long statusIcon;
		String customRingtone;
		boolean hasPhoneNumber;
		boolean isInVisibleGroup;
		boolean isStarred;
		boolean isSendToVoiceMail;
		int timesContacted;
		long lastTimeContacted;
		String vCardData;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return displayName;
		}

	}

}
