package org.tacademy.network.rss.chat;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;

public class ChattingManager {

	private XMPPConnection mXmppConnection;
	private ChatManager mChatManager;
	private ArrayList<ChatData> chatlist = new ArrayList<ChatData>();
	
	public ChattingManager(XMPPConnection connection) {
		mXmppConnection = connection;
		mChatManager = mXmppConnection.getChatManager();
		mChatManager.addChatListener(new ChatManagerListener() {

			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				// TODO Auto-generated method stub
				ChatData newChatData = new ChatData(chat);
				
			}
			
		});
		
	}
	
	public ChatData searchChatData(String jid) {
		for (ChatData data : chatlist) {
			if (data.getJID().equals(jid)) {
				return data;
			}
		}
		return null;
	}
	
	public boolean createChatData(String jid) {
		Chat chat = mChatManager.createChat(jid, null);
		ChatData chatData = new ChatData(chat);
		return true;
	}
}
