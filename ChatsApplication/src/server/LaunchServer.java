package server;


import server.clients.SocketsReceiver;
import server.data.ApplicationDatabase;
import server.data.ApplicationDatabaseFactory;
import server.data.DBWorker;
import server.login.Accounts;
import server.login.ProfileSender;
import server.server_state.ServerState;
import server.server_state.ServerStateFactory;

public class LaunchServer {
	
	static String database = "database.xml";
	
	public static void launchDatabase(int port, String databaseTitle){
		ApplicationDatabase database = ApplicationDatabaseFactory.getDatabase();
		ServerStateFactory.initServerState(database);
		ServerState server = ServerStateFactory.getServer();
		SocketsReceiver receiver = new SocketsReceiver(port);
	}
	
	public static void main(String [] args){
		launchDatabase(2018, database);
	}

}
