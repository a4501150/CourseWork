package team6.dao;

import javax.servlet.ServletContext;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public enum MongoDBDatabaseOperator implements IDatabaseOperator {
	INSTANCE;
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;

	private MongoDBDatabaseOperator() {
	}
	
	public MongoDatabase getMongoDatabase() {
		return mongoDatabase;
	}
	
	@Override
	public void initConnection() {
		mongoClient = new MongoClient("localhost", 27017);
		mongoDatabase = mongoClient.getDatabase("csp584_project");
	}

	@Override
	public void closeConnection() {
		if(mongoClient != null) {
			mongoClient.close();
			System.out.println("MongoDB connection closed.");
		}
	}

	@Override
	public void setupDb(ServletContext sc) {
	}

}
