package client.logic;

import client.ClientProfile;
import client.ClientView;
import client.GeneralSettings;
import client.database.ClientDatabase;
import client.gui.WindowImp;
import client.logic.entities.Contact;
import client.logic.entities.Message;
import client.logic.entities.ClientTalk;
import client.logic.handlers.UserMessageReceiver;
import client.net.Server;

public class TalkAssistant  implements UserMessageReceiver {
	
	private static ClientTalk empty = new ClientTalk("Empty conversation", 0, null, null);
	
	private ClientDatabase database = GeneralSettings.getDatabase();
	private Server server = Server.getServer();
	
	
	private Contact user;
	private ClientTalk talk;
	
	public TalkAssistant (){
		user = GeneralSettings.getUser();
	}
	
	public TalkAssistant (ClientProfile profile){
		user = new Contact (profile.getName());
		
	}

	public static ClientTalk getEmptyConversation() {
		return empty;
	}
	
	

	@Override
	public void receive(String messageContent) {
		if (talk == null){
			throw new AssertionError("Talk is null");
		}
		if (server == null){
			server = Server.getServer();
		}
		user = GeneralSettings.getUser();
		Message message = new Message(user, messageContent);
		//talk.addMessage(message);
		sendToDatabase(message);
		sendToServer(message);
		
	}

	private void sendToServer(Message message) {
		if (server == null){
			throw new AssertionError("Server is null");
		}
		server.sendMessage(message, talk.getId());
		
	}

	private void sendToDatabase(Message message) {
		database.addNewMessage(talk.getId(), message);
		
	}


	@Override
	public void setTalk(ClientTalk talk) {
		this.talk = talk;
		
	}
}
