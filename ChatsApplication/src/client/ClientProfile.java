package client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import client.logic.entities.ClientTalk;
import client.logic.entities.Contact;
import client.logic.entities.Message;

public class ClientProfile {
	
	private String name;
	private String password;
	private Set<Contact>profileContacts;
	private Collection <ClientTalk> talks;

	public ClientProfile(String name, String password, Set <Contact> profileContacts, Collection<ClientTalk> talks){
		this.name = name;
		this.password=password;
		if (profileContacts == null){
			profileContacts=new HashSet<Contact>();
		}
		this.profileContacts=profileContacts;
		if (talks == null){
			talks= new ArrayList<ClientTalk>();
		}
		this.talks=talks;
	}
	
	public ClientProfile (String name, String password){
		new ClientProfile(name, password, null);
	}
	
	public ClientProfile(String name, String password, Set<Contact> profileContacts) {
		new ClientProfile(name, password, profileContacts, null);
	}

	public void addTalk (ClientTalk talk){
		talks.add(talk);
	}

	public void setContacts(Set<Contact> contacts) {
		this.profileContacts=contacts;
		
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public Set<Contact> getContacts() {
		return profileContacts;
	}
	
	public Collection<ClientTalk> getTalks(){
		return talks;
	}

	public void addMessage(int talkID, Message message) {
		for (ClientTalk talk : talks){
			if (talk.getId()==talkID){
				talk.addMessage(message);
			}
		}
		
	}
	
	public void addContact(Contact contact) {
		profileContacts.add(contact);
		
	}
	
	@Override
	public String toString(){
		return name;
		
	}

	public void updateTalk(String title, int talkID, Set<Contact> participants, Queue<Message> messages) {
		ClientTalk target=null;
		for (ClientTalk talk : talks){
			if (talk.getId()==talkID){
				target=talk;
				break;
			}
		}
		target.update(title, participants, messages);
		
	}



}
