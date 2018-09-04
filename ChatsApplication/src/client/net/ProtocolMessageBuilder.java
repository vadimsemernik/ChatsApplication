package client.net;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;

import client.ClientProfile;
import client.logic.entities.ClientTalk;
import client.logic.entities.Contact;
import client.logic.entities.Message;
import client.net.client_side.net_protocol.Protocol;


public class ProtocolMessageBuilder {

	public Talks talks = new Talks();
	public Messages messages = new Messages();
	public Contacts contacts = new Contacts();
	
	
	public class Talks {
		
		public String getNewTalkItem (String title, int id, Set <Contact> participants){
			StringBuilder builder = new StringBuilder();
			builder.append(Protocol.INFO_HEADER);
			builder.append(Protocol.Delimiter.Message);
			builder.append(Protocol.NewItem.NewTalk);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(id);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(getParticipantsItem(participants));
			return builder.toString();
		}
		
		public String getNewParticipant(String name, int talkID){
			StringBuilder builder = new StringBuilder();
			builder.append(Protocol.INFO_HEADER);
			builder.append(Protocol.Delimiter.Message);
			builder.append(Protocol.NewItem.NewParticipant);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(talkID);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(name);
			return builder.toString();
		}
		
		public String getTalkItem(String title, int id, Set <Contact> participants, Queue <Message> messages){
			StringBuilder builder = new StringBuilder();
			builder.append(Protocol.INFO_HEADER);
			builder.append(Protocol.Delimiter.Message);
			builder.append(Protocol.LogicHeader.Talk);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(Integer.valueOf(id));
			builder.append(Protocol.Delimiter.Outer);
			builder.append(getParticipantsItem(participants));
			builder.append(Protocol.Delimiter.Outer);
			builder.append(getMessagesItem(messages));
			return builder.toString();
			
		}
		
		private String getMessagesItem(Queue<Message> messages) {
			StringBuilder builder = new StringBuilder();
			for (Message message : messages){
				builder.append(getMessage(message));
				builder.append(Protocol.Delimiter.Inner);
			}
			int last = builder.lastIndexOf(Protocol.Delimiter.Inner.toString());
			builder.delete(last, last+Protocol.Delimiter.Inner.toString().length());
			return builder.toString();
		}

		private String getMessage(Message message) {
			StringBuilder builder = new StringBuilder();
			builder.append( message.getAuthor().getName());
			builder.append(Protocol.Delimiter.Message);
			builder.append(message.getContent());
			return builder.toString();
		}

		private String getParticipantsItem(Set<Contact> participants) {
			if (participants == null){
				throw new IllegalArgumentException("Participants is null");
			}
			StringBuilder builder = new StringBuilder();
			for (Contact party : participants){
				builder.append(party.getName());
				builder.append(Protocol.Delimiter.Inner);
			}
			int last = builder.lastIndexOf(Protocol.Delimiter.Inner.toString());
			builder.delete(last, last+Protocol.Delimiter.Inner.toString().length());
			return builder.toString();
		}
		
	}
	
	public class Messages {
		
		public String getTalkMessageItem(String content, String author, int talkID) {
			StringBuilder builder = new StringBuilder();
			builder.append(Protocol.INFO_HEADER);
			builder.append(Protocol.Delimiter.Message);
			builder.append(Protocol.LogicHeader.Message);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(talkID);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(author);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(content);
			return builder.toString();
		}
		
	}
	
	public class Contacts {
		
		public String getNewContact(String name){
			StringBuilder builder = new StringBuilder();
			builder.append(Protocol.INFO_HEADER);
			builder.append(Protocol.Delimiter.Message);
			builder.append(Protocol.NewItem.NewContact);
			builder.append(Protocol.Delimiter.Outer);
			builder.append(name);
			return builder.toString();
		}
	}

	public String getUpdateMessage(ClientProfile profile) {
		int contactsCount = profile.getContacts().size();
		Collection<ClientTalk> talks = profile.getTalks();
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.LogicHeader.Login);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.Login.Update);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(profile.getName());
		builder.append(Protocol.Delimiter.Outer);
		builder.append(profile.getPassword());
		builder.append(Protocol.Delimiter.Outer);
		builder.append(contactsCount);
		builder.append(Protocol.Delimiter.Outer);
		for (ClientTalk talk : talks){
			builder.append(talk.getTitle());
			builder.append(Protocol.Delimiter.Message);
			builder.append(talk.getId());
			builder.append(Protocol.Delimiter.Message);
			builder.append(talk.getParticipants().size());
			builder.append(Protocol.Delimiter.Message);
			builder.append(talk.getMessagesCount());
			builder.append(Protocol.Delimiter.Inner);
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
		
	}

}
