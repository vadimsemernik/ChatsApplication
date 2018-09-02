package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Set;

import client.ClientView;
import client.database.ClientDatabase;
import client.database.ClientDatabaseContext;
import client.logic.handlers.LoginHandler;
import client.login.LoginWindow;
import client.net.Server;
import client.net.client_side.connections.InitialServerConnection;
import client.net.client_side.net_protocol.ProtocolWriter;
import fabric.ServerProfileFabric;
import fabric.ServerTalkFabric;
import server.LaunchServer;
import server.logic.entities.ServerMessage;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.protocol.MessageBuilderFactory;
import server.protocol.Protocol;
import server.protocol.ServerMessageBuilder;

public class Test {
	static String databaseName = "database.xml";
	static int port = 2018;
	static String login = "Deadpool";
	static String password = "12bullets";
	
	public static void main (String args[]) throws UnknownHostException, IOException{
		
		server();
		//check();
		
		
	}

	private static void server() throws UnknownHostException, IOException {
		LaunchServer.launchDatabase(port, databaseName);
		ClientDatabase database = ClientDatabase.getDatabase();
		ClientDatabaseContext.addProfilesToDatabase(databaseName, database);
		LoginWindow loginWindow = new LoginWindow("Title");
		LoginHandler handler = new LoginHandler(loginWindow);
		handler.handle(login, password);
		
	}

	private static void check() {
		char ch = '\r';
		String test = "test\r";
		char [] chars = test.toCharArray();
		for (char c : chars){
			if (c=='\r'){
				System.out.println("Test");
			} else{
				System.out.print(c);
			}
		}
		if (ch == (char)-1||ch =='\r'){
			System.out.println("true");
		} else {
			System.out.println(false);
		}
		
	}
	
	
	
	
}
