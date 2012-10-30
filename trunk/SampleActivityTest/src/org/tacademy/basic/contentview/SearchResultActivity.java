package org.tacademy.basic.contentview;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SearchResultActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	String queryString = intent.getStringExtra(SearchManager.QUERY);
	    	Toast.makeText(this, "Query : " + queryString, Toast.LENGTH_SHORT).show();
	    }
	}

}
