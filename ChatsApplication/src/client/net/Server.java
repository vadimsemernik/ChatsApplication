package client.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import client.ClientProfile;
import client.logic.entities.Message;
import client.logic.utils.SharedQueue;
import client.net.client_side.connections.InitialServerConnection;
import client.net.client_side.connections.ServerConnection;
import test.logging.ClientLog;

public class Server implements Closeable{
	
	private static Server singleton = new Server();
	private static InetAddress serverIP;
	private static int port = 2018;
	
	private ClientProfile profile;
	private boolean alreadyConnectedToServer = false;
	
	/*
	 * connection is responsible for sending text messages to server
	 */
	private ServerConnection connection;
	private InitialServerConnection initial;
	private Socket socket;
	
	/*
	 * builder is responsible for converting logic units to text message 
	 * under proper protocol format
	 */
	private ProtocolMessageBuilder builder=new ProtocolMessageBuilder();;
	
	
	
	public static Server getServer(){
		return singleton;
	}
	
	
	
	public static void setServer(ServerConnection connection){
		if (connection==null)throw new IllegalArgumentException("ServerConnection in setServer(connection) is null");
		if (singleton == null){
			singleton = new Server (connection);
		} else{
			if (singleton.connection!=null){
				if (connection.equals(singleton.connection)) return;
			}
			singleton.connection=connection;
			singleton.alreadyConnectedToServer=true;
		}
	}
	
	private Server (ServerConnection connection){
		this.connection = connection;
	}
	
	private Server(){
	}
	
	
	public void sendMessage(Message message, int talkID) {
		String author = message.getAuthor().getName();
		String content = builder.messages.getTalkMessageItem(message.getContent(), author, talkID);
		connection.sendMessage(content);
		
	}

	public String search(String login, String password) {
		String serverAnswer="";
		if (!alreadyConnectedToServer){
			initConnection();
			serverAnswer = initial.login(login, password);
		} else {
			serverAnswer = changeConnection(login, password);
		}
		return serverAnswer;
	}

	private String changeConnection(String login, String password) {
		String serverAnswer="";
		Socket socket = connection.getSocket();
		//close current server connection
		disconnect();
		try {
			initial = new InitialServerConnection(socket);
			//try to get new profile and create new server connection
			serverAnswer = initial.login(login, password);
		} catch (IOException e) {
			ClientLog.writeToLog("Server changeConnection IOException "+e.getMessage());
			e.printStackTrace();
		}
		return serverAnswer;
	}

	public static void bindClientProfile(ClientProfile profile) {
		if (singleton == null){
			throw new IllegalStateException("Server is not initialized yet!!!");
		} else {
			singleton.setProrile(profile);
		}
		
	}

	private void setProrile(ClientProfile profile) {
		this.profile = profile;	
	}

	public ClientProfile updateProfile(ClientProfile profile) {
		connect(profile);
		String message = builder.getUpdateMessage(profile);
		System.out.println(message);
		connection.sendMessage(message);
		return profile;
	}

	private void connect(ClientProfile profile) {
		if (!alreadyConnectedToServer){
			initConnection(profile);
		}
		
	}



	private void initConnection(ClientProfile profile) {
		try {
			serverIP = InetAddress.getLocalHost();
			Socket socket = new Socket(serverIP, port);
			connection = new ServerConnection(socket, profile, new SharedQueue<String> ());
			alreadyConnectedToServer = true;
		} catch (UnknownHostException e) {
			ClientLog.writeToLog("Server initConnection UnknownHostException "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e){
			ClientLog.writeToLog("Server initConnection IOException "+e.getMessage());
			e.printStackTrace();
		}
		
	}



	private void connect() {
		if (!alreadyConnectedToServer){
			initConnection();
		}
	}
	
	private void initConnection(){
		try {
			serverIP = InetAddress.getLocalHost();
			Socket socket = new Socket(serverIP, port);
			initial = new InitialServerConnection(socket);
			alreadyConnectedToServer = true;
		} catch (UnknownHostException e) {
			ClientLog.writeToLog("Server initConnection UnknownHostException "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e){
			ClientLog.writeToLog("Server initConnection IOException "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void disconnect(){
		if (!alreadyConnectedToServer) return;
		if (connection!=null){
			connection.close();
		}
		connection = null;
		alreadyConnectedToServer = false;
	}

	

	@Override
	public void close() throws IOException {
		if (connection!=null) connection.close();
		alreadyConnectedToServer=false;
		if (initial!=null) initial.close();
		if (socket!=null) socket.close();
	}
	
	
	public ClientProfile getProfile() {
		return profile;
	}
	
	

}
