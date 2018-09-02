package server.login;


import java.io.IOException;
import java.net.Socket;

import server.protocol.Protocol;
import server.protocol.ProtocolReader;
import server.protocol.ProtocolWriter;
import server.utils.ServiceMessages;

public class NewConnection {
	
	private Socket socket;
	private ProtocolReader reader;
	private ProtocolWriter writer;
	
	private ProfileState profileState;
	

	
	
	public NewConnection(Socket socket) throws IOException{
		this.socket = socket;
		String loginMessage;
		reader = new ProtocolReader (socket.getInputStream());
		writer = new ProtocolWriter(socket.getOutputStream());
		try{
			loginMessage = reader.readMessage();
			System.out.println("NewConnection: reader.readMessage");
			String serverAnswer = check(loginMessage);	
			handleServerAnswer(serverAnswer);	
			System.out.println("NewConnection: handleServerAnswer");
		} catch (IOException e){
			e.printStackTrace();
		}	
	}
	
	private void handleServerAnswer(String answer) throws IOException {
		if (answer == null){
			writer.writeMessage(invalidUser());
		} else {
			writer.writeMessage(answer);
			processServerAnswer(answer);	
		}	
	}


	private void processServerAnswer(String answer) throws IOException {
		System.out.println("NewConnection :processServerAnswer ");
		if (answer.startsWith(Protocol.Login.Found.toString())){					
			String [] parts = answer.split(Protocol.Delimiter.Message.toString());
			String client = parts[1];
			sendClientProfile(client);		
		} else {
			writer.writeMessage(accountNotFound());
		}
		
	}

	private String check(String loginMessage) {
		String result=null;
		String [] strings = loginMessage.split(Protocol.Delimiter.Outer.toString());
		if (strings[0].startsWith(Protocol.LogicHeader.Login.toString())){
			String login = strings[1];
			String password = strings[2];
			result = LoginChecker.checkAccount(login, password);
			if (strings[0].endsWith(Protocol.Login.Update.toString())){
				profileState = ProfileState.createInitialProfileState(strings[1], Integer.parseInt(strings[3]), strings[4]);
			}
		}
		return result;	
	}



	private boolean sendClientProfile(String client) {
		System.out.println("NewConnection: sendClientProfile");
		ProfileSender sender = new ProfileSender(profileState, socket, writer, reader);
		return sender.sendProfile (client);
	
	}


	private String accountNotFound() {
		return Protocol.SERVICE_HEADER
				+Protocol.Delimiter.Message
				+ServiceMessages.ACCOUNT_IS_NOT_FOUND;
	}

	private String invalidUser() {
		return Protocol.Login.NotFound.toString()
				+Protocol.Delimiter.Message.toString()
				+Protocol.Login.InvalidLogin.toString();
	}


	public Socket getSocket() {
		return socket;
	}
	
	

}
