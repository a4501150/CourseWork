package team6.listener;

import javax.servlet.*;

import team6.dao.MongoDBDatabaseOperator;
import team6.dao.MySQLDatabaseOperator;
import team6.model.DealMatches;

public class ContextListener implements ServletContextListener {
	
	private MySQLDatabaseOperator mySql = MySQLDatabaseOperator.INSTANCE;
	private MongoDBDatabaseOperator mongoDb = MongoDBDatabaseOperator.INSTANCE;
	private DealMatches deal = new DealMatches();
	
	@Override
    public void contextDestroyed(ServletContextEvent e) {
    	closeMySqlConnection();
    	closeMongoDbConnection();
    }

	@Override
	public void contextInitialized(ServletContextEvent e) {
		ServletContext sc = e.getServletContext();
		initMySqlConnection(sc);
		initMongoDbConnection(sc);
		
		deal.processDealMatch(sc);
	}

	private void initMySqlConnection(ServletContext sc) {
		mySql.initConnection();
		mySql.setupDb(sc);
	}
	
    private void closeMySqlConnection() {
    	mySql.closeConnection();
	}

	private void initMongoDbConnection(ServletContext sc) {
		mongoDb.initConnection();
	}

	private void closeMongoDbConnection() {
		mongoDb.closeConnection();
	}
}