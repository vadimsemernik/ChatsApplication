package server.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	private static DBConnection instance;
	private static String driver = "com.mysql.jdbs.Driver";
	private static String url = "jdbc:mysql://localhost:3306/storage";
	private static String user = "root";
	private static String password = "application";
	
	
	private DBConnection() {
		
	}
	
	
	private DBConnection(String url, String user, String password) {
		this.url=url;
		this.user = user;
		this.password = password;
	}

	public static DBConnection getInstance(){
		if (instance == null){
			instance=new DBConnection();
		}
		return instance;
	}
	
	public static DBConnection getInstance(String url, String user, String password){
		instance=new DBConnection(url, user, password);
		return instance;
	}
	
	public ResultSet getDBData(String query){
		Connection conn;
		Statement statement;
		ResultSet result=null;
			try {
				conn = DriverManager.getConnection(url, user, password);
				statement = conn.createStatement();
				result = statement.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
		return result;
	}
	
	public int changeData (String query){
		Connection conn;
		Statement statement;
		int result=0;
		try {
			conn = DriverManager.getConnection(url, user, password);
			statement = conn.createStatement();
			result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);														
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	}

}
