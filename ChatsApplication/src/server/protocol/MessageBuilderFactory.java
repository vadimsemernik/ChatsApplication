package server.protocol;

import server.server_state.ServerState;
import server.server_state.ServerStateFactory;

public class MessageBuilderFactory {
	
	private static ServerMessageBuilder builder;
	
	public static ServerMessageBuilder getMessageBuilder(){
		if (builder == null){
			builder = new ServerMessageBuilder();
		}
		return builder;
	}

}
