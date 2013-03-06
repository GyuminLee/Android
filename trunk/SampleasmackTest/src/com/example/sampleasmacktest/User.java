package com.example.sampleasmacktest;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

public class User {
	RosterEntry user;
	Presence presence;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return user.getUser();
	}
}
