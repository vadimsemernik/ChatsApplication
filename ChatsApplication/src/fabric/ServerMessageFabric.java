package fabric;

import server.logic.entities.ServerMessage;

public class ServerMessageFabric {
	
	private static String defaultContent = "MessageContent";
	private static int counter = 0;

	public static ServerMessage getMessage(String author) {
		counter++;
		ServerMessage message = new ServerMessage(author, defaultContent+counter);
		return message;
	}

}
