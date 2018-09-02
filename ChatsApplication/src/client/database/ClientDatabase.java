package client.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import client.ClientProfile;
import client.logic.entities.Contact;
import client.logic.entities.Message;
import client.logic.entities.ClientTalk;
import client.net.Server;

public class ClientDatabase {
	
	private static ClientDatabase singleton;
	
	private Map <String,ClientProfile> profiles = new HashMap<String, ClientProfile>();
	private Map <String, Contact> contacts = new HashMap<String, Contact>();
	private Map <Integer, ClientTalk> talks = new HashMap<Integer, ClientTalk>();
	
	public static ClientDatabase getDatabase(){
		if (singleton == null){
			singleton = new ClientDatabase();
		}
		return singleton;
	}
	
	
	private ClientDatabase () {
		profiles = new HashMap<String, ClientProfile>();
		contacts = new HashMap<String, Contact>();
		talks = new HashMap<Integer, ClientTalk>();
	}

	public ClientProfile search(String login, String password) {
		ClientProfile result = profiles.get(login);
		if (result != null && password.equals(result.getPassword())){
			addTalksToProfile(result);
			return result;
		}
		return null;
	}
	
	private void addTalksToProfile(ClientProfile result) {
		// TODO Auto-generated method stub
		
	}


	public void addProfile (ClientProfile profile){
		profiles.put(profile.getName(), profile);
		Set<Contact> set = profile.getContacts();
		for (Contact contact : set){
			contacts.put(contact.getName(), contact);
		}
		Collection <ClientTalk> collection = profile.getTalks();
		for (ClientTalk talk: collection){
			talks.put(talk.getId(), talk);
		}
		
	}
	
	public void addTalk(ClientTalk talk) {
		talks.put(talk.getId(), talk);
		
	}
	
	public ClientProfile getProfile (String name){
		return profiles.get(name);
	}

	public void addNewMessage(int talkID, Message message) {
		ClientTalk talk = talks.get(talkID);
		if (talk != null){
			talk.addMessage(message);
		}
		
	}



}
