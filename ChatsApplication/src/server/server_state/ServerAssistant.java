package server.server_state;

import server.logic.entities.ServerMessage;
import server.protocol.ServerMessageBuilder;

public class ServerAssistant {
	
	private ServerMessageBuilder builder;
	
	ServerAssistant(){
		builder = new ServerMessageBuilder();
	}
	
	public String createTalkMessageMessage(int talkID, ServerMessage message){
		return builder.getServerMessageMessage(message, talkID);
	}

}
