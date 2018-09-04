package client.net.client_side.profile_updating;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import client.ClientProfile;
import client.logic.entities.ClientTalk;
import client.logic.entities.Contact;
import client.logic.entities.Message;
import client.net.client_side.messages_parsing_logic.ErrorWarnings;
import client.net.client_side.net_protocol.Protocol;

public class ProfileUpdater {
	
	private ClientProfile profile;

	
	public ProfileUpdater(ClientProfile profile){
		if (profile == null){
			throw new IllegalArgumentException("profile is null");
		}
		this.profile = profile;
	}
	
	public void parseMessage (String message){
		// extract logic header
		String [] messageParts = message.split(Protocol.Delimiter.Outer.toString());
		String header  = (messageParts[0].split(Protocol.Delimiter.Message.toString()))[1];
		int headerSize = Integer.valueOf(Protocol.Size.Header.toString());
		if (Protocol.LogicHeader.Talk.toString().equals(header)) {
			addTalk(messageParts, headerSize);
		} else if (Protocol.LogicHeader.Contact.toString().equals(header)) {
			addContact(messageParts, headerSize);
		} else if (Protocol.LogicHeader.Message.toString().equals(header)) {
			addMessage(messageParts, headerSize);
		} else if (Protocol.LogicHeader.ContactsUpdate.toString().equals(header)) {
			updateContacts(messageParts, headerSize);
		} else if (Protocol.LogicHeader.TalkUpdate.toString().equals(header)) {
			updateTalk(messageParts, headerSize);
		} else {
			throw new IllegalArgumentException(ErrorWarnings.INVALID_SERVER_MESSAGE);
		}
	}
	
	private void updateTalk(String[] messageParts, int index) {
		String title = messageParts[index++];
		int id = Integer.valueOf(messageParts[index++]);
		Set <Contact> participants = getParticipants(messageParts[index++]);
		Queue <Message> messages = getMessages(messageParts[index]);
		profile.updateTalk(title, id, participants, messages);
		
	}

	

	private void updateContacts(String[] messageParts, Integer index) {
		String contactsDescription = messageParts[index];
		String [] contacts = contactsDescription.split(Protocol.Delimiter.Inner.toString());
		for (String contact : contacts){
			profile.addContact(new Contact(contact));
		}
	}

	private void addMessage(String[] components, int index) {
		if (components.length!=Integer.valueOf(Protocol.Size.Message.toString())){
			throw new IllegalArgumentException(ErrorWarnings.INVALID_MESSAGE);
		}
		int talkID = Integer.valueOf(components[index++]);
		Message message = new Message(new Contact(components[index++]), components[index++]);
		
		profile.addMessage(talkID, message);
	}

	private void addContact(String[] components, int index) {
		if (components.length!=Integer.valueOf(Protocol.Size.Contact.toString())){
			throw new IllegalArgumentException(ErrorWarnings.INVALID_CONTACT);
		}
		profile.addContact(new Contact(components[index]));
	}

	private void addTalk(String[] components, int i) {
		if (components.length!=Integer.valueOf(Protocol.Size.Message.toString())){
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
		String[] parts = message.split(Protocol.Delimiter.Inner.toString());
		String header = parts[0];
		int participantsCount = Integer.valueOf(header.split(Protocol.Delimiter.Message.toString())[1]);
		Set <Contact> participants = new HashSet<Contact>();
		if (participantsCount>0){
			for (int i = 1; i<=participantsCount;i++){
				participants.add(new Contact(parts[i]));
			}
		}
		return participants;
	}
	
	private Queue<Message> getMessages(String message) {
		String [] parts = message.split(Protocol.Delimiter.Inner.toString());
		String header = parts[0];
		int messagesCount = Integer.valueOf(header.split(Protocol.Delimiter.Message.toString())[1]);
		Queue <Message> messages = new LinkedList <Message>();
		if (messagesCount>0){
			for (int i=1; i<=messagesCount;i++){
				messages.add(initMessage(parts[i]));
			}
		}
		return messages;
	}

	private Message initMessage(String text) {
		String [] parts = text.split(Protocol.Delimiter.Message.toString(),2);
		Message message = null;
		if (parts.length==2){
			message = new Message(new Contact(parts[0]), parts[1]);
		}
		return message;
	}

	
}
