package client;

import java.net.InetAddress;

import client.database.ClientDatabase;
import client.logic.entities.Contact;
import client.net.Server;
import client.net.client_side.connections.ServerConnection;

public class GeneralSettings {
	
	private static InetAddress serverIP;
	private static int port = 2018;
	
	private static Contact user;
	private static ClientDatabase database = ClientDatabase.getDatabase();
	private static Server server;
	private static ClientView view;
	
	private static int connectionID = 0;
	
	
	public static void setUser(Contact contact){
		user=contact;
	}
	
	public static Contact getUser(){
		return user;
	}

	public static ClientDatabase getDatabase() {
		return database;
	}

	public static ClientView getView() {
		return view;
	}

	public static void setView(ClientView view) {
		GeneralSettings.view = view;
	}

	public static void setProfile(ClientProfile profile, ServerConnection connection) {
		view = new ClientView(profile);
		Server.setServer(connection);
		Server.bindClientProfile(profile);
		server = Server.getServer();
		
		
	}

	public static void setProfile(ClientProfile profile) {
		view = new ClientView(profile);
		
	}

	public static int getConnectionID() {
		return ++connectionID;
	}
	
	
	
	

}
