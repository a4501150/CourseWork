package team6.model;

import javax.servlet.http.HttpSession;

import team6.dao.UserDAO;
import team6.entity.Role;
import team6.entity.User;

public class Authenticator {

	private UserDAO userDao;
	
	public Authenticator() {
		userDao = new UserDAO();
	}
	/**
	 * Do login function. Return an User object if login correct, null otherwise.
	 */
	public User doLogin(String username, String password) {
		return userDao.selectUser(username, password);
	}
	
	/**
	 * Log out user 
	 */
	public void doLogout(HttpSession session) {
		session.invalidate();
	}
	
	/**
	 * Do register function. Return an User object if successful, null otherwise
	 */
	public User doRegister(String username, String password, String email, Role role) {
		User userInDb = userDao.selectUser(username);
		
		if(userInDb == null) {
			userDao.insertUser(username, password, email, role);
			User newUser = userDao.selectUser(username, password);
			return newUser;
		}
		
		return null;
	}
	
}
