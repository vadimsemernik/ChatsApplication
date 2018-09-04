package client.net.client_side.connections;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

import client.ClientProfile;
import client.GeneralSettings;
import client.logic.utils.SharedQueue;
import client.net.client_side.messages_parsing_logic.ErrorWarnings;
import client.net.client_side.messages_parsing_logic.ServerMessagesParser;
import client.net.client_side.net_protocol.Protocol;
import test.logging.ClientLog;

public class ServerConnection implements Closeable{

	private Socket socket;
	
	private volatile boolean running;
	private SharedQueue <String> messagesToSend;
	private SharedQueue <String> messagesToReceive;
	private MessageReader reader;
	private MessageWriter writer;
	
	private int connectionID;
	
	/*
	 * continuously parsing server messages 
	 */
	private ServerMessagesParser parser;
	
	/*
	 * messagesToReceive is a SharedQueue from InitialConnection
	 */
	public ServerConnection (Socket socket, ClientProfile profile, SharedQueue<String> messagesToReceive) {
		this.socket = socket;
		connectionID = GeneralSettings.getConnectionID();
		messagesToSend = new SharedQueue <String>();
		this.messagesToReceive = messagesToReceive;
		//parser gets a queue of messages to parse
		parser = new ServerMessagesParser(profile, this);
		parser.bindMessagesQueue(messagesToReceive);
		running = true;
		try {
			initReader();
		} catch (IOException e) {
			ClientLog.writeToLog("ServerConnection constructor initReader IOException "+e.getMessage());
			System.out.println(ErrorWarnings.READER_ERROR);
			e.printStackTrace();
		}
		try {
			initWriter(messagesToSend);
		} catch (IOException e) {
			ClientLog.writeToLog("ServerConnection constructor initWriter IOException "+e.getMessage());
			System.out.println(ErrorWarnings.WRITER_ERROR);
			e.printStackTrace();
		}	
	}

	private void initWriter(SharedQueue <String> messages) throws IOException {
		writer = new MessageWriter(socket.getOutputStream(), messages);
		
	}

	private void initReader() throws IOException {
		reader = new MessageReader(socket.getInputStream(), messagesToReceive);
		
	}

	public boolean sendMessage(String message) {
		return messagesToSend.add(message);
	}
	
	public void sendConfirmation() {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.SERVICE_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.Message.OK);
		sendMessage(builder.toString());
		
	}
	
	public boolean receiveMessage(String message){
		return messagesToReceive.add(message);
	}
	
	
	

	@Override
	public boolean equals(Object object) {
		if (object == null || !(object instanceof ServerConnection)){
			return false;
		}
		ServerConnection connection = (ServerConnection) object;
		if (connectionID == connection.connectionID){
			return true;
		}
		return false;
	}

	@Override
	public void close(){
		running = false;
			try {
				if (socket!=null) socket.close();
				if (reader != null)reader.close();
				if (writer != null) writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public Socket getSocket() {
		return socket;
	}
	
	
	
	
}
