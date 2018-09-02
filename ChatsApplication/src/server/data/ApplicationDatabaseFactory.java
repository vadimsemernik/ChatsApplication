package server.data;

public class ApplicationDatabaseFactory {
	
	private static String defaultURL = "jdbc:mysql://localhost:3306/storage";
	private static String defaultUser = "root";
	private static String defaultPassword = "application";
	
	public static ApplicationDatabase getDatabase(String url, String user, String password){
		return new DBWorker(url, user, password);
	}
	
	public static ApplicationDatabase getDatabase(){
		return new DBWorker(defaultURL, defaultUser, defaultPassword);
	}

}
