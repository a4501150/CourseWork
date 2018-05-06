package team6.dao;

import javax.servlet.ServletContext;

public interface IDatabaseOperator {
	
	public void initConnection();
	
	public void closeConnection();
	
	/**
	 * Import settings from script file.
	 */
	public void setupDb(ServletContext sc);

}
