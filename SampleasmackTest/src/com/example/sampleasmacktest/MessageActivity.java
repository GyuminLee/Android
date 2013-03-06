package com.example.sampleasmacktest;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sampleasmacktest.XMPPManager.OnChatListener;
import com.example.sampleasmacktest.XMPPManager.OnMessageSendListener;

public class MessageActivity extends Activity {

	public static final String PARAM_USER = "user";
	
	ListView mList;
	ArrayAdapter<String> mAdapter;
	ArrayList<String> mData;
	EditText inputView;
	Chat mChat;
	String mUser;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.message_layout);
	    mList = (ListView)findViewById(R.id.listView1);
	    mData = new ArrayList<String>();
	    mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);
	
	    mList.setAdapter(mAdapter);
	    
	    inputView = (EditText)findViewById(R.id.editText1);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String message = inputView.getText().toString();
				XMPPManager.getInstance().sendMessage(mChat, message, new OnMessageSendListener() {
					
					@Override
					public void onMessageSendSuccess(Chat chat, String message) {
						// TODO Auto-generated method stub
						mAdapter.add(mUser + ":" + message);
					}
					
					@Override
					public void onMessageSendFail(Chat chat, String message) {
						// TODO Auto-generated method stub
						Toast.makeText(MessageActivity.this, "send fail : " + message, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	    mUser = getIntent().getStringExtra(PARAM_USER);
	    mChat = XMPPManager.getInstance().createChat(mUser, new OnChatListener() {
			
			@Override
			public void onChatMessageReceived(Chat chat, Message message) {
				// TODO Auto-generated method stub
				mAdapter.add(message.getFrom() + ":" + message.getBody());
			}
		});
	}

}
