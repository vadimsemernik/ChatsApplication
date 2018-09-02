package test;

import java.io.IOException;
import java.net.UnknownHostException;

import client.database.ClientDatabase;
import client.database.ClientDatabaseContext;
import client.login.MainPage;

public class ClientLauncher {
	
	static int port = 2018;
	static String login = "Cable";
	static String password = "2049year";
	static String databaseName = "database.xml";

	public static void main(String[] args) throws UnknownHostException, IOException {
		ClientDatabase database = ClientDatabase.getDatabase();
		ClientDatabaseContext.addProfilesToDatabase(databaseName, database);
		MainPage page = new MainPage();

	}

}
