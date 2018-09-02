package client.database;

import client.ClientProfile;

public class TestClientDatabase {
	
	static String databaseTitle = "database.xml";
	static String login = "Deadpool";
	static String password = "12bullets";

	public static void main(String[] args) {
		ClientDatabase database = ClientDatabase.getDatabase();
		ClientDatabaseContext.addProfilesToDatabase(databaseTitle, database);
		ClientProfile profile = database.search(login, password);
		System.out.println(profile.getName());

	}

}
