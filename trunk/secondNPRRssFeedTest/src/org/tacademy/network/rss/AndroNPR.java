package org.tacademy.network.rss;

import org.tacademy.network.rss.board.BoardList;
import org.tacademy.network.rss.board.MakeQRCodeActivity;
import org.tacademy.network.rss.database.DataProcessThread;
import org.tacademy.network.rss.google.GmailAtomFeedActivity;
import org.tacademy.network.rss.navermovie.NaverMovieActivity;
import org.tacademy.network.rss.npr.NPRNewsDetails;
import org.tacademy.network.rss.npr.SingleNewsItem;
import org.tacademy.network.rss.upload.UploadActivity;
import org.tacademy.network.rss.yahooplace.YahooPlacesActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AndroNPR extends ParentActivity {
    /** Called when the activity is first created. */
	ArrayAdapter<DisplayItem> aa;
	ListView myListView;
	Context context;
	SingleNewsItem selectedNewsItem;
	Handler handler = new Handler();
	
	String[] myUrlAddress = {
			"http://www.npr.org/rss/rss.php?id=1001",
			"http://www.npr.org/rss/rss.php?id=1003",
			"http://www.npr.org/rss/rss.php?id=1004",
			"http://www.npr.org/rss/rss.php?id=1006",
			"http://www.npr.org/rss/rss.php?id=1007",
			"http://www.npr.org/rss/rss.php?id=1008",
			"http://www.npr.org/rss/rss.php?id=1012",
			"http://www.npr.org/rss/rss.php?id=1021",
			"http://www.npr.org/rss/rss.php?id=1057"
	};
	
	String[] myUrlCaption = {
			"Top Stories",
			"U.S. News",
			"World News",
			"Business",
			"Health & Science",
			"Arts & Entertainment",
			"Politices & Society",
			"People & Places",
			"Opinion"
	};
	
	DisplayItem[] myDisplayItem = {
		new DisplayItem("Top Stories","http://www.npr.org/rss/rss.php?id=1001",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("U.S. News","http://www.npr.org/rss/rss.php?id=1003",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("World News","http://www.npr.org/rss/rss.php?id=1004",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("Business","http://www.npr.org/rss/rss.php?id=1006",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("Health & Science","http://www.npr.org/rss/rss.php?id=1007",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("Arts & Entertainment","http://www.npr.org/rss/rss.php?id=1008",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("Politices & Society","http://www.npr.org/rss/rss.php?id=1012",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("People & Places","http://www.npr.org/rss/rss.php?id=1021",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("Opinion","http://www.npr.org/rss/rss.php?id=1057",DisplayItem.TYPE_NPR_NEWS),
		new DisplayItem("Java News Search","java",DisplayItem.TYPE_NAVER_SEARCH_NEWS)
	};
	
//	String[] myUrlAddress2 = new String[myUrlAddress.length];
//	String[] myUrlCaption2 = new String[myUrlCaption.length];
//	ArrayList<DisplayItem> myItemList = new ArrayList<DisplayItem>();
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        context = getApplicationContext();
        
        this.setTitle("NPR Headline News v0.0");
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),YahooPlacesActivity.class);
				startActivity(i);
			}
		});
        btn = (Button)findViewById(R.id.naver);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),NaverMovieActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.upload);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),UploadActivity.class);
				startActivity(i);
			}
		});
        btn = (Button)findViewById(R.id.board);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),BoardList.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.chat);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),MakeQRCodeActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.gmail);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),GmailAtomFeedActivity.class);
				startActivity(i);
			}
		});
        myListView = (ListView)findViewById(R.id.ListView01);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> _av, View _v, int _index,
					long _id) {
				// TODO Auto-generated method stub
//				String urlAddress = myUrlAddress[_index];
//				String urlCaption = myUrlCaption[_index];
				DisplayItem myItem = myDisplayItem[_index];
				
				Intent NprNewsDetailsIntent = new Intent(AndroNPR.this,NPRNewsDetails.class);
				//Bundle myData = new Bundle();
				//myData.putString("urlAddress", urlAddress);
				//myData.putString("urlCaption", urlCaption);
				//NprNewsDetailsIntent.putExtras(myData);
				NprNewsDetailsIntent.putExtra("myData", myItem);
				startActivity(NprNewsDetailsIntent);
				
			}
		});
        
        aa = new ArrayAdapter<DisplayItem>(this, android.R.layout.simple_list_item_1,myDisplayItem);
        myListView.setAdapter(aa);
        
        DataProcessThread th = DataProcessThread.getInstance();
        th.startThread(this, handler);
//		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
//		registrationIntent.putExtra("app", PendingIntent.getBroadcast(AndroNPR.this, 0, new Intent(), 0));
//		registrationIntent.putExtra("sender", "dongja94@gmail.com");
//		startService(registrationIntent);
        
    }
    
    
    
    
}