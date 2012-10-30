package org.tacademy.network.rss.chat;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class ChatData {
	Chat mChat;
	ArrayList<ChatMessage> messageList = new ArrayList<ChatMessage>();
	String jid;
	
	public ChatData(Chat chat) {
		mChat = chat;
		mChat.addMessageListener(new MessageListener() {

			@Override
			public void processMessage(Chat chat, Message message) {
				// TODO Auto-generated method stub
				if (mChat.getThreadID().equals(chat.getThreadID())) {
					ChatMessage msg = new ChatMessage();
					msg.setType(ChatMessage.MESSAGE_TYPE_RECEIVE);
					msg.setMessage(message);
					messageList.add(msg);
				}
			}
			
		});
	}
	
	public String getJID() {
		return jid;
	}
}
