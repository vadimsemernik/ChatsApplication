package server.server_state;

import server.data.ApplicationDatabase;


public class ServerStateFactory {
	
	private static ServerState serverState;
	
	public static void initServerState(ApplicationDatabase database){
		serverState = new ServerState(database);
	}
	
	public static ServerState getServer(){
		return serverState;
	}

}
