package client.net.client_side;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

import client.ClientProfile;
import client.ClientView;
import client.GeneralSettings;
import client.logic.utils.SharedQueue;
import client.net.Server;
import client.net.client_side.connections.InitialServerConnection;
import client.net.client_side.connections.ServerConnection;
import client.net.client_side.messages_parsing_logic.ServerMessagesParser;
import client.net.client_side.messages_parsing_logic.UnknownMessageException;
import client.net.client_side.net_protocol.Protocol;
import client.net.client_side.profile_initialization.ClientProfileBuilder;

public class ProfileLoader implements Closeable{
	
	private ClientProfileBuilder  clientBuilder;
	private Thread worker;
	private SharedQueue <String> messages;
	private Socket server;
	private volatile boolean finish = false;


	public ProfileLoader(InitialServerConnection connection) {
		
		clientBuilder = new ClientProfileBuilder();
		messages = connection.getServerMessages();
		server = connection.getSocket();
		worker = new Worker();
	}
	
	public void start() {
		worker.start();
		
	}
	
	public ProfileLoader(InitialServerConnection connection, ClientProfile profile){
		clientBuilder = new ClientProfileBuilder();
		messages = connection.getServerMessages();
		server = connection.getSocket();
		worker = new Worker();
	}
	
	private void finishBuilding(){
		finish = true;
	}
	
	public void waitForProfileLoad() {
		while (!finish){
			Thread.yield();
		}
		
	}
	
	
	private class Worker extends Thread {
		
		private volatile boolean running;
		
		private String finish = Protocol.SERVICE_HEADER
				+ Protocol.Delimiter.Message
				+ Protocol.Message.End;
		
		private Worker(){
			running = true;
		}
		
		public void run () {
			while (running){
				String message = messages.getNext();
				parseMessage(message);
			}
			finishBuilding();
		}


		private void parseMessage(String message){
			if (message.startsWith(finish)){
				running = false;
				return;
			}
			clientBuilder.buildProfileFromMessage(message);
			
		}
	}
	
	public ClientProfile getProfile(){
		return clientBuilder.getProfile();
	}

	public SharedQueue<String> getMessages() {
		return messages;
	}

	@Override
	public void close() throws IOException {
		if (worker.isAlive()){
			worker.interrupt();
		}
		
		
	}
	
	



	

}
