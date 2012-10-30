package org.tacademy.basic.searchlocal;

import android.content.ContentProvider;
import android.content.SearchRecentSuggestionsProvider;

public class SearchProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "org.tacademy.basic.searchlocal.searchprovider";
	
	public final static int MODE = DATABASE_MODE_QUERIES;
	
	public SearchProvider() {
		super();
		this.setupSuggestions(AUTHORITY, MODE);
	}

}
