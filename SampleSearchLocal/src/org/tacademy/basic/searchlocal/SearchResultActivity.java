package org.tacademy.basic.searchlocal;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SearchRecentSuggestions;

public class SearchResultActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    processSearchQuery(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		processSearchQuery(intent);
	}
	
	public void processSearchQuery(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String queryString = intent.getStringExtra(SearchManager.QUERY);
			Bundle appData = intent.getBundleExtra(SearchManager.APP_DATA);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchProvider.AUTHORITY, SearchProvider.MODE);
			suggestions.saveRecentQuery(queryString, null);
			// contact의 경우 SampleProviderContacts 참조
			// application의 경우 SampleProviderApplications 참조
		} else if (ContactsContract.Intents.SEARCH_SUGGESTION_CLICKED.equals(intent.getAction())) {
			Uri data = intent.getData();
			// LookupUri를 이용하는 방법은 SampleProviderContacts 참조
		} else if (Intent.ACTION_MAIN.equals(intent.getAction())) {
			Uri data = intent.getData();
			// SampleProviderApplications를 참조.
		} else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Uri data = intent.getData();
			String path = data.toString();
			if (path.startsWith("content://media/external/audio/media/")) {
				
			} else if (path.startsWith("content://media/external/audio/albums/")) {
				
			} else if (path.startsWith("content://media/external/audio/artists/")) {
				
			}
		}
		
	}
}
