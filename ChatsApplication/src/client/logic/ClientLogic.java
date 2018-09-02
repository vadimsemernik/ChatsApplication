package client.logic;

import client.ClientProfile;
import client.gui.WindowImp;
import client.logic.handlers.UserMessageReceiver;


public class ClientLogic {
	
	private TalkAssistant assistant;
	
	public ClientLogic(ClientProfile profile) {
		this.assistant = new TalkAssistant(profile);
	}

	public TalkAssistant getAssistant() {
		return assistant;
	}

	public UserMessageReceiver getMessageReceiver() {
		return assistant;
	}

	
	
	

}
