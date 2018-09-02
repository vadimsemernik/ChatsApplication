package client.logic.handlers;

import client.logic.entities.ClientTalk;

public interface UserMessageReceiver {
	
	public void receive(String message);
	
	public void setTalk(ClientTalk talk);

}
