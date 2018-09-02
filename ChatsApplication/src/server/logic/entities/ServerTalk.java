package server.logic.entities;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import client.logic.entities.ClientTalk;

public class ServerTalk {
	
	private String title;
	private int id;
	private Collection<TalkParticipant> talkParticipants;
	private Collection <ServerMessage> talkMessages;
	
	private int participantsCount;
	private int messagesCount;
	private int hash=0;

	public ServerTalk(String title, int id, Collection<TalkParticipant> participants, Collection<ServerMessage> messages) {
		this.title = title;
		this.id = id;
		this.talkParticipants = participants;
		this.talkMessages = messages;
		participantsCount = participants.size();
		messagesCount = messages.size(); 
		
	}

	
	public  Collection<TalkParticipant> getParticipants() {
		return talkParticipants;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof ServerTalk){
			ServerTalk talk = (ServerTalk) obj;
			return title.equals(talk.title)
					&&id==talk.getId()
					&&participantsCount==talk.participantsCount
					&&messagesCount==talk.getMessagesCount();
		}
		return false;
	}
	
	
	@Override
	public int hashCode(){
		if (hash == 0){
			int result = 21;
			result = 31*result + title.hashCode();
			result = 31*result + id;
			result = 31*result + messagesCount+ participantsCount;
			hash=result;
		}
		return hash;
	}
	
	@Override
	public String toString(){
		return title;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Collection<ServerMessage> getMessages() {
		return talkMessages;
	}
	
	public void addMessage(ServerMessage message){
		talkMessages.add(message);
		messagesCount++;
	}
	
	public void addParticipant(TalkParticipant candidate){
		for (TalkParticipant participant : talkParticipants){
			if (participant.equals(candidate)){
				return;
			}
		}
		talkParticipants.add(candidate);
		participantsCount++;
	}

	/*public Collection<ServerMessage> getMessages(int from) {
		Collection <ServerMessage> messages = talkMessages.subList(from, talkMessages.size());
		return messages;
	}

	public Collection<String> getParticipants(int from) {
		Collection <TalkParticipant> participants = talkParticipants.subList(from, talkParticipants.size());
		return null;
	}*/
	
	public int getMessagesCount(){
		return messagesCount;
	}
	
	public int getParticipantsCount(){
		return participantsCount;
	}
	

	

	
	
	
	

}
