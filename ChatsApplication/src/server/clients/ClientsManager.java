package server.clients;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import server.clients.connections.ClientConnection;
import server.logic.entities.TalkParticipant;

public class ClientsManager {
	
	/*
	 * connected clients to server
	 */
	private static Map <String, ServerClient> liveClients = new HashMap<String, ServerClient>();
	
	public static void addConnetion(ClientConnection connection){
		System.out.println("Add connection");
		if (connection == null){
			System.out.println("Connection is null");
			return;
		}
		ServerClient client = liveClients.get(connection.getClientName());
		if (client==null){
			client = new ServerClient(connection.getClientName());
			client.addConnection(connection);
			liveClients.put(client.getName(), client);
		} else {
			client.addConnection(connection);
		}
		
	}
	
	public static void deleteConnection(ClientConnection connection){
		String clientName = connection.getClientName();
		ServerClient client = liveClients.get(clientName);
		if (client !=null){
			int remainingConnections = client.deleteConnection(connection);
			if (remainingConnections==0){
				liveClients.remove(clientName);
			}
		}
	}
	
	
	public static void sendMessageToConnectedClients (String message, Collection<TalkParticipant> participants){
		ServerClient client;
		for (TalkParticipant target: participants){
			client = liveClients.get(target.getName());
			if (client == null) continue;
			client.sendMessage(message);
		}
	}

	public static void changeConnection(ClientConnection oldConnection, ClientConnection newConnection) {
		System.out.println("Change connection");
		ServerClient client = liveClients.get(oldConnection.getClientName());
		client.changeConnection(oldConnection, newConnection);
		
	}
	
	

}
