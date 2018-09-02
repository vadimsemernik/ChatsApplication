package client.net.client_side.messages_parsing_logic;

import client.ClientProfile;
import client.logic.utils.SharedQueue;
import client.net.client_side.net_protocol.Protocol;
import client.net.client_side.profile_updating.ProfileUpdater;

public class ServerMessagesParser {
	
	/*
	 * updater updates current profile with
	 * according to new messages
	 */
	private ProfileUpdater updater;
	private SharedQueue<String> newMessages;
	private boolean running;
	private Thread worker;
	

	public ServerMessagesParser(ClientProfile profile) {
		updater = new ProfileUpdater(profile);
		newMessages = new SharedQueue<String>();
		running = true;
		worker = new Worker ();
		worker.start();
	}
	
	private void newMessage(String message){
		if (message.equals("")){
			System.out.println("Empty message");
			return;
		}
		if (message.startsWith(Protocol.SERVICE_HEADER)){
			parseServiceMessage(message);
		} else if (message.startsWith(Protocol.INFO_HEADER)){
			updater.parseMessage(message);
		} else {
			try {
				throw new UnknownMessageException(message);
			} catch (UnknownMessageException e) {
				e.printStackTrace();
			}
		}
	}

	private void parseServiceMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	public void bindMessagesQueue(SharedQueue<String> messages) {
		newMessages = messages;
		
	}
	
	
	private class Worker extends Thread{
		
		private String title = "MessagesParserWorker";
		
		private Worker(){
			this.title=title;
		}
		
		@Override
		public void run () {
			while (running){
				String message = newMessages.getNext();
				newMessage(message);
			}
		}
	}
}
