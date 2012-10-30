package org.tacademy.basic.providercontact;

import java.util.ArrayList;

import org.tacademy.basic.providercontact.ShowDisplayName.ContactsData;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ShowDetails extends Activity {

	ContactsData mData;
	Uri contactDataUri;
	Uri rawContactUri;
	Uri lookupContactUri;
	ArrayList<Uri> rawContactIds = new ArrayList<Uri>();
	LinearLayout itemLayout;
	
	private final static String CRLF = "\n\r";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show_details);
	    itemLayout = (LinearLayout)findViewById(R.id.itemlayout);
	    
	    Intent intent = getIntent();
	    mData = (ContactsData) intent.getExtras().getSerializable("item");

	    rawContactUri = RawContacts.CONTENT_URI;
	    Cursor c = getContentResolver().query(rawContactUri, new String[] {
	    		RawContacts._ID, RawContacts.CONTACT_ID
	    		}, RawContacts.CONTACT_ID + "=?", new String[] {
	    		String.valueOf(mData.rowId)
	    		}, null);
	    while(c.moveToNext()) {
	    	long id = c.getLong(c.getColumnIndex(RawContacts._ID));
	    	rawContactIds.add(Uri.withAppendedPath(RawContacts.CONTENT_URI, String.valueOf(id)));
	    }
	    c.close();
	    
	    // rawContactUri로 부터 Contacts의 lookupUri 얻기
	    if (rawContactIds.size() > 0) {
	    	lookupContactUri = RawContacts.getContactLookupUri(getContentResolver(), rawContactIds.get(0));
	    }
	    
	    contactDataUri = Uri.withAppendedPath(Uri.withAppendedPath(Contacts.CONTENT_URI, String.valueOf(mData.rowId)),
	    		Contacts.Data.CONTENT_DIRECTORY);
	    
	    // contactDataUri로 부터 data 구하기
	    
	    c = getContentResolver().query(contactDataUri, null, null, null, null);
	    
	    while(c.moveToNext()) {
	    	String mimetypes = c.getString(c.getColumnIndex(Data.MIMETYPE));
	    	String text = null;
	    	Bitmap bm = null;
	    	if (mimetypes.equalsIgnoreCase(CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) {
	    		text = getNameField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
	    		text = getPhoneField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
	    		text = getEmailField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
	    		text = getNickNameField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
	    		text = getOrganizationField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)) {
	    		text = getPostalField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)) {
	    		text = getGroupField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Website.CONTENT_ITEM_TYPE)) {
	    		text = getWebSizeField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Note.CONTENT_ITEM_TYPE)) {
	    		text = getNoteField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Event.CONTENT_ITEM_TYPE)) {
	    		text = getEventField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Im.CONTENT_ITEM_TYPE)) {
	    		text = getImField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Relation.CONTENT_ITEM_TYPE)) {
	    		text = getRelationField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE)) {
	    		text = getSipAdressField(c);
	    	} else if (mimetypes.equalsIgnoreCase(CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
	    		bm = getPhotoField(c);
	    	}
	    	
	    	if (text != null) {
	    		TextView tv = new TextView(this);
	    		tv.setText(text);
	    		itemLayout.addView(tv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
	    	} else if (bm != null) {
	    		ImageView iv = new ImageView(this);
	    		iv.setImageBitmap(bm);
	    		itemLayout.addView(iv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
	    	}
	    	
	    }
	    
	    
	    // TODO Auto-generated method stub
	}
	
	private Bitmap getPhotoField(Cursor c) {
		// TODO Auto-generated method stub
		Bitmap bm = null;
		byte[] bytes = c.getBlob(c.getColumnIndex(CommonDataKinds.Photo.PHOTO));
		if (bytes != null) {
			bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		}
		return bm;
	}

	private String getSipAdressField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Sip : ").append(c.getString(c.getColumnIndex(CommonDataKinds.SipAddress.SIP_ADDRESS))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.SipAddress.TYPE));
		switch(type) {
			case CommonDataKinds.SipAddress.TYPE_HOME :
				sb.append("type : ").append("home").append(CRLF);
				break;
			case CommonDataKinds.SipAddress.TYPE_OTHER :
				sb.append("type : ").append("other").append(CRLF);
				break;
			case CommonDataKinds.SipAddress.TYPE_WORK :
				sb.append("type : ").append("work").append(CRLF);
				break;
			case CommonDataKinds.SipAddress.TYPE_CUSTOM :
				sb.append("type : ").append("custom").append(CRLF);
				break;
			default :
				break;
		}
		sb.append("label : ").append(c.getString(c.getColumnIndex(CommonDataKinds.SipAddress.LABEL)));
		return sb.toString();
	}

	private String getRelationField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("name : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Relation.NAME))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.Relation.TYPE));
		switch(type) {
			case CommonDataKinds.Relation.TYPE_ASSISTANT :
				sb.append("type : ").append("assistant").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_BROTHER :
				sb.append("type : ").append("brother").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_CHILD :
				sb.append("type : ").append("child").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_DOMESTIC_PARTNER :
				sb.append("type : ").append("domestic partner").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_FATHER :
				sb.append("type : ").append("father").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_FRIEND :
				sb.append("type : ").append("friend").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_MANAGER :
				sb.append("type : ").append("manager").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_MOTHER :
				sb.append("type : ").append("mother").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_PARENT :
				sb.append("type : ").append("parent").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_PARTNER :
				sb.append("type : ").append("partner").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_REFERRED_BY :
				sb.append("type : ").append("referred by").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_RELATIVE :
				sb.append("type : ").append("relative").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_SISTER :
				sb.append("type : ").append("sister").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_SPOUSE :
				sb.append("type : ").append("spouse").append(CRLF);
				break;
			case CommonDataKinds.Relation.TYPE_CUSTOM :
				sb.append("type : ").append("custom").append(CRLF);
				break;
			default :
				break;

		}
		return sb.toString();
	}

	private String getImField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("im : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Im.DATA))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.Im.TYPE));
		switch(type) {
			case CommonDataKinds.Im.TYPE_HOME :
				sb.append("type : ").append("home").append(CRLF);
				break;
			case CommonDataKinds.Im.TYPE_OTHER :
				sb.append("type : ").append("other").append(CRLF);
				break;
			case CommonDataKinds.Im.TYPE_WORK :
				sb.append("type : ").append("work").append(CRLF);
				break;
			case CommonDataKinds.Im.TYPE_CUSTOM :
				sb.append("type : ").append("custom").append(CRLF);
				break;
			default :
				break;
		}
		sb.append("protocol : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Im.PROTOCOL))).append(CRLF);
		sb.append("custom protocol : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Im.CUSTOM_PROTOCOL))).append(CRLF);
		return sb.toString();
	}

	private String getEventField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("event : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Event.START_DATE))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.Event.TYPE));
		switch(type) {
			case CommonDataKinds.Event.TYPE_ANNIVERSARY :
				sb.append("type : ").append("anniversary").append(CRLF);
				break;
			case CommonDataKinds.Event.TYPE_BIRTHDAY :
				sb.append("type : ").append("birthday").append(CRLF);
				break;
			case CommonDataKinds.Event.TYPE_OTHER :
				sb.append("type : ").append("other").append(CRLF);
				break;
			case CommonDataKinds.Event.TYPE_CUSTOM :
				sb.append("type : ").append("custom").append(CRLF);
				break;
			default :
				break;
		}
		return sb.toString();
	}

	private String getNoteField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("note : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Note.NOTE))).append(CRLF);
		
		return sb.toString();
	}

	private String getWebSizeField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("url : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Website.URL))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.Website.TYPE));
		switch(type) {
			case CommonDataKinds.Website.TYPE_BLOG :
				sb.append("type : ").append("blog").append(CRLF);
				break;
			case CommonDataKinds.Website.TYPE_FTP :
				sb.append("type : ").append("ftp").append(CRLF);
				break;
			case CommonDataKinds.Website.TYPE_HOME :
				sb.append("type : ").append("home").append(CRLF);
				break;
			case CommonDataKinds.Website.TYPE_HOMEPAGE :
				sb.append("type : ").append("homepage").append(CRLF);
				break;
			case CommonDataKinds.Website.TYPE_OTHER :
				sb.append("type : ").append("other").append(CRLF);
				break;
			case CommonDataKinds.Website.TYPE_PROFILE :
				sb.append("type : ").append("profile").append(CRLF);
				break;
			case CommonDataKinds.Website.TYPE_WORK :
				sb.append("type : ").append("work").append(CRLF);
				break;
			case CommonDataKinds.Website.TYPE_CUSTOM :
				sb.append("type : ").append("custom").append(CRLF);
				break;
			default :
				break;
		}
		
		return sb.toString();
	}

	private String getGroupField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}

	private String getPostalField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("address : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.StructuredPostal.TYPE));
		switch(type) {
			case CommonDataKinds.StructuredPostal.TYPE_HOME :
				sb.append("type : ").append("home").append(CRLF);
				break;
			case CommonDataKinds.StructuredPostal.TYPE_OTHER :
				sb.append("type : ").append("other").append(CRLF);
				break;
			case CommonDataKinds.StructuredPostal.TYPE_WORK :
				sb.append("type : ").append("work").append(CRLF);
				break;
			case CommonDataKinds.StructuredPostal.TYPE_CUSTOM :
				sb.append("type : ").append("custom").append(CRLF);
				break;
			default :
				break;
		}
		sb.append("street : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredPostal.STREET))).append(CRLF);
		sb.append("pobox : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredPostal.POBOX))).append(CRLF);
		sb.append("neighborhood : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredPostal.NEIGHBORHOOD))).append(CRLF);
		sb.append("city : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredPostal.CITY))).append(CRLF);
		sb.append("region : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredPostal.REGION))).append(CRLF);
		sb.append("postcode : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredPostal.POSTCODE))).append(CRLF);
		sb.append("country : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredPostal.COUNTRY))).append(CRLF);
		return sb.toString();
	}

	private String getOrganizationField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("company : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Organization.COMPANY))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.Organization.TYPE));
		switch(type) {
			case CommonDataKinds.Organization.TYPE_OTHER :
				sb.append("type : ").append("other").append(CRLF);
				break;
			case CommonDataKinds.Organization.TYPE_WORK :
				sb.append("type : ").append("work").append(CRLF);
				break;
			case CommonDataKinds.Organization.TYPE_CUSTOM :
				sb.append("type : ").append("custom").append(CRLF);
				break;
			default :
				break;
		}
		sb.append("label : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Organization.LABEL))).append(CRLF);
		sb.append("title : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Organization.TITLE))).append(CRLF);
		sb.append("department : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Organization.DEPARTMENT))).append(CRLF);
		sb.append("job_description : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Organization.JOB_DESCRIPTION))).append(CRLF);
		sb.append("symbol : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Organization.SYMBOL))).append(CRLF);
		sb.append("phonetic name : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Organization.PHONETIC_NAME))).append(CRLF);
		sb.append("office location : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Organization.OFFICE_LOCATION))).append(CRLF);
		
		return sb.toString();
	}

	private String getNickNameField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("nickname : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Nickname.NAME))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.Nickname.TYPE));
		switch(type) {
			case CommonDataKinds.Nickname.TYPE_DEFAULT :
				sb.append("type : ").append("default").append(CRLF);
				break;
			case CommonDataKinds.Nickname.TYPE_INITIALS :
				sb.append("type : ").append("initials").append(CRLF);
				break;
			case CommonDataKinds.Nickname.TYPE_MAIDEN_NAME :
				sb.append("type : ").append("maiden name").append(CRLF);
				break;
			case CommonDataKinds.Nickname.TYPE_OTHER_NAME :
				sb.append("type : ").append("other name").append(CRLF);
				break;
			case CommonDataKinds.Nickname.TYPE_SHORT_NAME :
				sb.append("type : ").append("short name").append(CRLF);
				break;
			case CommonDataKinds.Nickname.TYPE_CUSTOM :
				sb.append("type : ").append("custom").append(CRLF);
				break;
			default :
				break;
		}
		sb.append("label : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Nickname.LABEL))).append(CRLF);
		return sb.toString();
	}

	private String getEmailField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("email : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Email.DISPLAY_NAME))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.Email.TYPE));
		switch(type) {
			case CommonDataKinds.Email.TYPE_HOME :
				sb.append("type : ").append("home").append(CRLF);
				break;
			case CommonDataKinds.Email.TYPE_MOBILE :
				sb.append("type : ").append("mobile").append(CRLF);
				break;
			case CommonDataKinds.Email.TYPE_OTHER :
				sb.append("type : ").append("other").append(CRLF);
				break;
			case CommonDataKinds.Email.TYPE_WORK :
				sb.append("type : ").append("work").append(CRLF);
				break;
			case CommonDataKinds.Email.TYPE_CUSTOM :
				sb.append("type : ").append("other").append(CRLF);
				break;
			default :
				break;
		}
		return sb.toString();
	}

	private String getPhoneField(Cursor c) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("number : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Phone.NUMBER))).append(CRLF);
		int type = c.getInt(c.getColumnIndex(CommonDataKinds.Phone.TYPE));
		switch(type) {
			case CommonDataKinds.Phone.TYPE_ASSISTANT :
				sb.append("type : ").append("assistant").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_CALLBACK :
				sb.append("type : ").append("callback").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_CAR :
				sb.append("type : ").append("car").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_COMPANY_MAIN :
				sb.append("type : ").append("company main").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_FAX_HOME :
				sb.append("type : ").append("fax home").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_FAX_WORK :
				sb.append("type : ").append("fax work").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_HOME :
				sb.append("type : ").append("home").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_ISDN :
				sb.append("type : ").append("isdn").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_MAIN :
				sb.append("type : ").append("main").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_MMS :
				sb.append("type : ").append("mms").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_MOBILE :
				sb.append("type : ").append("mobile").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_OTHER :
				sb.append("type : ").append("other").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_OTHER_FAX :
				sb.append("type : ").append("other fax").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_PAGER :
				sb.append("type : ").append("pager").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_RADIO :
				sb.append("type : ").append("radio").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_TELEX :
				sb.append("type : ").append("telex").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_TTY_TDD :
				sb.append("type : ").append("tty tdd").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_WORK :
				sb.append("type : ").append("work").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_WORK_MOBILE :
				sb.append("type : ").append("work mobile").append(CRLF);
				break;
			case CommonDataKinds.Phone.TYPE_WORK_PAGER :
				sb.append("type : ").append("work pager").append(CRLF);
				break;
			default :
				break;
		}
		sb.append("lable : ").append(c.getString(c.getColumnIndex(CommonDataKinds.Phone.LABEL))).append(CRLF);
		return sb.toString();
	}

	private String getNameField(Cursor c) {
		StringBuilder sb = new StringBuilder();
		sb.append("DisplayName : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredName.DISPLAY_NAME))).append(CRLF);
		sb.append("GivenName : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredName.GIVEN_NAME))).append(CRLF);
		sb.append("MiddleName : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredName.MIDDLE_NAME))).append(CRLF);
		sb.append("FamilyName : ").append(c.getString(c.getColumnIndex(CommonDataKinds.StructuredName.FAMILY_NAME))).append(CRLF);
		return sb.toString();
	}

}
