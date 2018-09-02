package client.net.client_side.profile_initialization;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import client.ClientProfile;
import client.logic.entities.*;
import client.net.client_side.connections.ServerConnection;
import client.net.client_side.messages_parsing_logic.ErrorWarnings;
import client.net.client_side.messages_parsing_logic.UnknownMessageException;
import client.net.client_side.net_protocol.Protocol;

public class ClientProfileBuilder {
	
	private ClientProfile profile;
	private String name;
	private String password;
	private Set<Contact>profileContacts = new HashSet<Contact>();
	private int size = 4;
	private int messageParts=2;
	
	

	public void parseMessage(String message) throws UnknownMessageException{
		String [] components = message.split(Protocol.Delimiter.Outer.toString());
		if (components.length<size){
			return;
		} else {
			String header = components[0];
			if (header.endsWith(Protocol.LogicHeader.Profile.toString())){
				initProfileComponents(components, 1);
			} else if (header.endsWith(Protocol.LogicHeader.Talk.toString())){
				addTalkToProfile(components, 1);
			} else {
				System.out.println("Unknown message");
				throw new UnknownMessageException("Invalid message in profile building");
			}
		}
	}
	
	private void initProfileComponents(String[] parts, int i) {
		System.out.println("Profile initialization");
		name = parts[i++];
		password = parts[i++];
		setContacts(parts[i++]);
		profile = new ClientProfile (name, password, profileContacts, null);
		
	}

	private void setContacts(String string) {
		String [] contactsNames = string.split(Protocol.Delimiter.Inner.toString());
		System.out.println("Contacts count: "+ contactsNames.length);
		for (String contactName:contactsNames){
			profileContacts.add(new Contact(contactName));
		}
	}


	private void addTalkToProfile(String[] components, int i) {
		if (profile == null){
			throw new IllegalArgumentException(ErrorWarnings.INVALID_PROFILE);
		}
		if (components.length-i<size){
			throw new IllegalArgumentException(ErrorWarnings.INVALID_TALK);
		}
		String title = components[i++];
		int talkID = Integer.valueOf(components[i++]);
		Set<Contact> participants = getParticipants(components[i++]);
		Queue <Message> messages = getMessages(components[i]);
		ClientTalk talk = new ClientTalk(title, talkID, participants, messages);
		profile.addTalk(talk);
		
	}


	private Set<Contact> getParticipants(String message) {
		String[] names = message.split(Protocol.Delimiter.Inner.toString());
		Set <Contact> participants = new HashSet<Contact>();
		for (String name : names){
			participants.add(new Contact(name));
		}
		return participants;
	}


	private Queue<Message> getMessages(String message) {
		String [] textMessages = message.split(Protocol.Delimiter.Inner.toString());
		Queue <Message> messages = new LinkedList <Message>();
		for (String text : textMessages){
			messages.add(initMessage(text));
		}
		return messages;
	}


	private Message initMessage(String text) {
		String [] parts = text.split(Protocol.Delimiter.Message.toString(),2);
		Message message = null;
		if (parts.length==messageParts){
			message = new Message(new Contact(parts[0]), parts[1]);
		}
		return message;
	}


	


	public void buildProfileFromMessage(String[] messages) {
		for (String message:messages){
			try {
				parseMessage(message);
			} catch (UnknownMessageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void buildProfileFromMessage(String message) {
		try {
			parseMessage(message);
		} catch (UnknownMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ClientProfile getProfile() {
		return profile;
	}

	



}
