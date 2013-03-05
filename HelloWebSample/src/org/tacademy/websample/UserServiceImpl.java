package org.tacademy.websample;

import java.util.ArrayList;

import org.tacademy.shared.User;
import org.tacademy.shared.UserService;

public class UserServiceImpl implements UserService {

	ArrayList<User> users = new ArrayList<User>();
	
	@Override
	public User createUser(String userName, String firstName, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		user.userName = userName;
		user.firstName = firstName;
		user.password = password;
		users.add(user);
		return user;
	}

	@Override
	public User createUser(String userName, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		user.userName = userName;
		user.password = password;
		users.add(user);
		return user;
	}

	@Override
	public User findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		for (User user : users) {
			if (user.userName.equals(userName)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		return users.size();
	}
}
