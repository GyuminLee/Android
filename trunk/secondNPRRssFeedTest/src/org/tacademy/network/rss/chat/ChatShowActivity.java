package org.tacademy.network.rss.chat;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatShowActivity extends ParentActivity {

	TextView nameView;
	ListView chatList;
	EditText messageView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.chat_show);
	
	    nameView = (TextView)findViewById(R.id.name);
	    chatList = (ListView)findViewById(R.id.chatShowList);
	    messageView = (EditText)findViewById(R.id.chatMessage);
	    // TODO Auto-generated method stub
	}

}
