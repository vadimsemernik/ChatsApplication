package server.clients.connections;


import java.net.Socket;

import server.login.ProfileLoadConnection;



public class CleanConnection extends ClientConnection{
	
	public CleanConnection(String name, Socket socket){
		super(name,  socket);
	}

	public CleanConnection(ProfileLoadConnection connection) {
		super(connection.name, connection.socket);
		super.id = connection.id;
		super.reader=connection.reader;
		super.writer=connection.writer;
	}

}
