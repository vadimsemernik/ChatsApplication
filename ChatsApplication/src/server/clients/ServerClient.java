package server.clients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

import server.clients.connections.CleanConnection;
import server.clients.connections.ClientConnection;
import server.logic.messageParsers.MessageParsingExecutor;
import server.logic.messageParsers.ParsingTask;
import server.server_state.ServerState;
import server.server_state.ServerStateFactory;
import server.utils.SharedQueue;

public class ServerClient {
	
	/*
	 * connections with same account
	 */
	private Collection <ClientConnection> connections;
	private String clientName;
	private int connectionsCounter=0;
	private boolean running;
	
	/*
	 * receiver redirects messages from clients to server
	 */
	private Receiver receiver;
	/*
	 * sender sends messages from server to clients
	 */
	private Sender sender;
	private SharedQueue <String> messagesToSend;
	private SharedQueue <String> messagesToReceive;
	
	public ServerClient(String clientName){
		this.clientName = clientName;
		connections= new ArrayList <ClientConnection>();
		messagesToSend = new SharedQueue<String>();
		messagesToReceive = new SharedQueue<String>();
		running = true;
		receiver = new Receiver();
		sender = new Sender();
		receiver.start();
		sender.start();
	}

	public void addConnection(ClientConnection connection) {
		if (connection != null){
			connectionsCounter++;
			connection.setConnectionId(connectionsCounter);
			connection.bindReceiveQueue(messagesToReceive);
			connections.add(connection);
		}
	}
	
	public void sendMessage (String message){
		messagesToSend.add(message);
	}

	public String getName() {
		return clientName;
	}
	
	
	private class Sender extends Thread {
		
		public void run () {
			while(running){
				String message = messagesToSend.getNext();
				for (ClientConnection connection:connections){
					connection.sendMessage(message);
				}
			}
		}
		
	}
	
	
	private class Receiver extends Thread{
		
		
		public void run () {
			while(running){
				String message = messagesToReceive.getNext();
				ParsingTask task = new ParsingTask(message);
				MessageParsingExecutor.addTask(task);
			} 
		}
		
	}


	public int deleteConnection(ClientConnection connection) {
		if (connections == null || connections.isEmpty()){
			return 0;
		}
		connections.remove(connection);
		return connections.size();
		
	}

	public void changeConnection(ClientConnection oldConnection, ClientConnection newConnection) {
		if (oldConnection.getConnectionId() != newConnection.getConnectionId()) return;
		connections.remove(oldConnection);
		connections.add(newConnection);
		
	}
	
	
	
	

}
