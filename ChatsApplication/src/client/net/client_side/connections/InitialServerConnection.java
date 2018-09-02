package client.net.client_side.connections;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

import client.ClientProfile;
import client.GeneralSettings;
import client.logic.utils.SharedQueue;
import client.net.client_side.ProfileLoader;
import client.net.client_side.messages_parsing_logic.ErrorWarnings;
import client.net.client_side.net_protocol.Protocol;
import client.net.client_side.net_protocol.ProtocolReader;
import client.net.client_side.net_protocol.ProtocolWriter;
import test.logging.ClientLog;

public class InitialServerConnection  implements Closeable{
	
	private Socket socket;
	private SharedQueue <String> serverMessages;
	private ProtocolWriter writer;
	private ProtocolReader reader;
	
	/*
	 * load profile information through server connection
	 */
	private ProfileLoader loader;
	
	public InitialServerConnection (Socket socket)  throws IOException{
		this.socket = socket;
		writer = new ProtocolWriter(socket.getOutputStream());
		reader = new  ProtocolReader(socket.getInputStream());
	}
	
	public String login(String name, String password) {
		String message = createLoginMessage(name, password);
		String serverAnswer = "";
		try{
			writer.writeMessage(message);
			serverAnswer = reader.readMessage();
		} catch (IOException e) {
			ClientLog.writeToLog("InitialServerConnection login IOException "+e.getMessage());
			System.out.println(ErrorWarnings.WRITER_ERROR);
			e.printStackTrace();
		}
		/*
		 * if server confirmed login, prepares for receiving profile data
		 */
		processServerAnswer(serverAnswer);
		return serverAnswer;
		
	}

	private void processServerAnswer(String serverAnswer) {
		if (serverAnswer.startsWith(Protocol.Login.Found.toString())){
			ProfileMessagesReceiver receiver = new ProfileMessagesReceiver();		//inner class
			serverMessages = new SharedQueue<String>();
			receiver.start();
			loader = new ProfileLoader(this);
			/*
			 * launch separate thread for profile downloading
			 */
			loader.start();
			loader.waitForProfileLoad();
			receiver.finish();			
			sendConfirmation();			//confirmation to server about success profile load
			finishSettings();
		} 
		
	}

	private void finishSettings() {
		ClientProfile client = loader.getProfile();
		ServerConnection connection = new ServerConnection(socket, client, loader.getMessages());
		// sets client gui and server 
		GeneralSettings.setProfile(client, connection);
		
	}

	private void sendConfirmation() {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.SERVICE_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.Message.OK);
		builder.append(Protocol.END);
		try {
			socket.getOutputStream().write(builder.toString().getBytes());
		} catch (IOException e) {
			ClientLog.writeToLog("InitialServerConnection sendConfirmation IOException "+e.getMessage());
			System.out.println(ErrorWarnings.WRITER_ERROR);
			e.printStackTrace();
		}
		
	}


	private String createLoginMessage(String name, String password) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.LogicHeader.Login);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.Login.Load);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(name);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(password);
		return builder.toString();
	}
	
	

	public Socket getSocket() {
		return socket;
	}

	public SharedQueue<String> getServerMessages() {
		return serverMessages;
	}





	private class ProfileMessagesReceiver extends Thread{
		
		private volatile boolean running;
		private String finish = Protocol.SERVICE_HEADER
				+ Protocol.Delimiter.Message
				+ Protocol.Message.End;
				
		
		
		private ProfileMessagesReceiver () {
			running = true;
		}
		
		
		public void finish() {
			running = false;
			
		}


		@Override
		public void run(){
			try {
				while (running){
					String message = reader.readMessage();
					if (message.startsWith(finish)){
						running = false;
					}
					serverMessages.add(message);
					
				}
			} catch (IOException e) {
				ClientLog.writeToLog("InitialServerConnection ProfileMessagesReceiver run IOException "+e.getMessage());
				System.out.println(ErrorWarnings.READER_ERROR);
				e.printStackTrace();
			} 
		}
	}


	@Override
	public void close() throws IOException {
		if (socket!=null) socket.close();
		if (reader!=null) reader.close();
		if (writer!=null) writer.close();
		if (loader!=null) loader.close();
		
	}

}
