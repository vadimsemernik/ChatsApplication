package client.logic.handlers;

import client.logic.TalkAssistant;
import client.logic.entities.ClientTalk;

public class ReceiversFactory {
	
	public static UserMessageReceiver getMessageReceiver(ClientTalk talk){
		TalkAssistant assistant = new TalkAssistant();
		assistant.setTalk(talk);
		return assistant;
	}

}
