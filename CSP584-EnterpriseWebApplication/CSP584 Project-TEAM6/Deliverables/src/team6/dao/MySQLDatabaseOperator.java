package team6.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import team6.utils.ScriptRunner;

public enum MySQLDatabaseOperator implements IDatabaseOperator {
	INSTANCE;

	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	public static final String DB_URL="localhost";
	public static final int DB_PORT= 3306;
	public static final String DB_SCHEMA = "csp584_project";
	public static final String DB_OPTION = "?useSSL=false";
	public static final String DB_CONFIG_FILE_PATH = "resources/sql/setup.sql";

    //  Database credentials
	public static final String USER = "root";
	public static final String PASS = "root";
	
	private Connection conn;

	private MySQLDatabaseOperator() {
		
	      try
	      {
	          Class.forName(JDBC_DRIVER).newInstance();
	      } 
	      catch (Exception e) 
	      {
	           e.printStackTrace();
	      }
	      
	}
	
	public Connection getConnection() {
		if(conn == null) {
			initConnection();
		}
		return conn;
	}

	@Override
	public void initConnection() {
		if(conn != null) {
			return;
		}
		try {
	        String sqlUrl = "jdbc:mysql://"
	            +   DB_URL
	            +   ":"
	            +   DB_PORT
	            +   "/" + DB_SCHEMA
	            +   DB_OPTION
	        ;
	        conn = DriverManager.getConnection(sqlUrl, USER, PASS);
	
	        System.out.println("Connected to MySQL server " + sqlUrl);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
    }
	
	@Override
    public void closeConnection() {
        if(conn != null) {
            try {
                conn.close();
                System.out.println("MySQL connection closed.");
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	@Override
	public void setupDb(ServletContext sc) {
		String sqlFilePath = sc.getRealPath(DB_CONFIG_FILE_PATH);
		ScriptRunner runner = new ScriptRunner(conn, false, false);
		try(Reader reader = new BufferedReader(new FileReader(sqlFilePath))) {
			runner.runScript(reader);
		}
		catch(IOException | SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
}
