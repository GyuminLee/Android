package com.example.testasmack;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.testasmack.XMPPManager.OnMessageReceiveListener;
import com.example.testasmack.XMPPManager.OnMessageSendListener;

public class ChatListActivity extends Activity {

	public static final String PARAM_USER_ID = "userid";
	
	String userid;
	ListView listView;
	EditText messageView;
	ArrayAdapter<String> mAdapter;
	Chat mChat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.chat_list);
	    listView = (ListView)findViewById(R.id.messageList);
	    messageView = (EditText)findViewById(R.id.sendMessage);
	    userid = getIntent().getStringExtra(PARAM_USER_ID);
	    mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<String>());
	    listView.setAdapter(mAdapter);
	    Button btn = (Button)findViewById(R.id.btnSend);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String message = messageView.getText().toString();
				XMPPManager.getInstance().sendMessage(mChat, message, new OnMessageSendListener() {
					
					@Override
					public void onMessageSended(String message) {
						mAdapter.add("me : " + message);
					}
				});
			}
		});
	    
	    mChat = XMPPManager.getInstance().createChat(userid, new OnMessageReceiveListener() {
			
			@Override
			public void onMessageReceived(String userid, String message) {
				mAdapter.add(userid + " : " + message);
			}
		});
	}

}
