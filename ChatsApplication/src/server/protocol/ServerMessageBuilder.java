package server.protocol;

import java.util.Collection;

import server.logic.entities.ServerMessage;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.logic.entities.TalkParticipant;


public class ServerMessageBuilder {



	public String getProfileMessage(ServerProfile profile) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.INFO_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.LogicHeader.Profile);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(profile.getName());
		builder.append(Protocol.Delimiter.Outer);
		builder.append(profile.getPassword());
		builder.append(Protocol.Delimiter.Outer);
		String contactsMessage = getContactsMessage(profile.getContacts());
		builder.append(contactsMessage);
		builder.append(Protocol.END);
		return builder.toString();
	}


	private String getContactsMessage(Collection<TalkParticipant> collection) {
		StringBuilder builder = new StringBuilder();
		for (TalkParticipant contact : collection){
			builder.append(contact.getName());
			builder.append(Protocol.Delimiter.Inner);
		}
		int last = builder.lastIndexOf(Protocol.Delimiter.Inner.toString());
		builder.delete(last, last+Protocol.Delimiter.Inner.toString().length());
		return builder.toString();
		
	}


	public String getTalkMessage(ServerTalk talk) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.INFO_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.LogicHeader.Talk);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(talk.getTitle());
		builder.append(Protocol.Delimiter.Outer);
		builder.append(String.valueOf(talk.getId()));
		builder.append(Protocol.Delimiter.Outer);
		String participantsMessage = getParticipantsMessage(talk.getParticipants());
		builder.append(participantsMessage);
		builder.append(Protocol.Delimiter.Outer);
		String messagesMessage = getMessagesMessage(talk.getMessages());
		builder.append(messagesMessage);
		builder.append(Protocol.END);
		return builder.toString();
	}


	private String getParticipantsMessage(Collection<TalkParticipant> participants) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.LogicHeader.ParticipantsCount);
		builder.append(Protocol.Delimiter.Message);
		builder.append(participants.size());
		builder.append(Protocol.Delimiter.Inner);
		for (TalkParticipant participant : participants){
			builder.append(participant.getName());
			builder.append(Protocol.Delimiter.Inner);
		}
		int last = builder.lastIndexOf(Protocol.Delimiter.Inner.toString());
		builder.delete(last, last+Protocol.Delimiter.Inner.toString().length());
		return builder.toString();
	}


	private String getMessagesMessage(Collection<ServerMessage> messages) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.LogicHeader.MessagesCount);
		builder.append(Protocol.Delimiter.Message);
		builder.append(messages.size());
		builder.append(Protocol.Delimiter.Inner);
		for (ServerMessage message : messages){
			builder.append(getMessage(message));
			builder.append(Protocol.Delimiter.Inner);
		}
		int last = builder.lastIndexOf(Protocol.Delimiter.Inner.toString());
		builder.delete(last, last+Protocol.Delimiter.Inner.toString().length());
		return builder.toString();
	}


	private String getMessage(ServerMessage message) {
		StringBuilder builder = new StringBuilder();
		builder.append( message.getAuthor());
		builder.append(Protocol.Delimiter.Message);
		builder.append(message.getContent());
		return builder.toString();
	}


	public String getServerMessageMessage(ServerMessage message, int talkID) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.INFO_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.LogicHeader.Message);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(talkID);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(message.getAuthor());
		builder.append(Protocol.Delimiter.Outer);
		builder.append(message.getContent());
		builder.append(Protocol.END);
		return builder.toString();
		
		
		
	}


	public String getParticipantMessage(String participant, int talkID) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.INFO_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.LogicHeader.Participant);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(talkID);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(participant);
		builder.append(Protocol.END);
		return builder.toString();
	}


	public String getContactMessage(String contact) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.INFO_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.LogicHeader.Contact);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(contact);
		builder.append(Protocol.END);
		return builder.toString();
	}


	public String getFinishMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.SERVICE_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.Message.End);
		builder.append(Protocol.END);
		return builder.toString();
	}


	public String getUpdateContactsMessage(Collection<TalkParticipant> newContacts) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.INFO_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.LogicHeader.ContactsUpdate);
		builder.append(Protocol.Delimiter.Outer);
		for (TalkParticipant contact : newContacts){
			builder.append(contact.getName());
			builder.append(Protocol.Delimiter.Inner);
		}
		return builder.toString();
	}


	public String getTalkUpdateMessage(int id, String title, Collection<TalkParticipant> participants,
			Collection<ServerMessage> messages) {
		StringBuilder builder = new StringBuilder();
		builder.append(Protocol.INFO_HEADER);
		builder.append(Protocol.Delimiter.Message);
		builder.append(Protocol.LogicHeader.TalkUpdate);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(title);
		builder.append(Protocol.Delimiter.Outer);
		builder.append(String.valueOf(id));
		builder.append(Protocol.Delimiter.Outer);
		if (participants != null){
			String participantsMessage = getParticipantsMessage(participants);
			builder.append(participantsMessage);
			builder.append(Protocol.Delimiter.Outer);
		}
		if (messages != null){
			String messagesMessage = getMessagesMessage(messages);
			builder.append(messagesMessage);
		}
		
		builder.append(Protocol.END);
		return builder.toString();
	}


	


}
