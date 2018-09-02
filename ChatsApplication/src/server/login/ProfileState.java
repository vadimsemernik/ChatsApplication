package server.login;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import server.logic.entities.TalkID;
import server.protocol.Protocol;

public class ProfileState {
	
	private String name;
	private int contactsCount;
	private Collection <TalkState> talkStates;
	private int talksCount;
	
	
	public static ProfileState createInitialProfileState(String name, int contactsVersion, String talksDescription) {
		ProfileState state = new ProfileState(name,contactsVersion,talksDescription);
		return state;
	}
	
	
	private ProfileState (String name, int contactsCount, String talksDescription){
		this.name = name;
		this.contactsCount=contactsCount;
		fillTalks(talksDescription);
	}
	
	public ProfileState (String name, int contactsCount){
		this.name = name;
		this.contactsCount=contactsCount;
		talkStates = new LinkedList<TalkState>();
	}
	
	public void setTalksCount(int count){
		this.talksCount=count;
	}
	
	public void addTalkState(int talkID, int participantsCount, int messagesCount) {
		TalkState state = new TalkState(talkID, participantsCount, messagesCount);
		talkStates.add(state);
		
	}
	
	private void fillTalks(String talksDescription) {
		talkStates = new LinkedList<TalkState>();
		TalkState state;
		int count = Integer.valueOf(Protocol.Size.TalkDescription.toString());
		String [] nodes = talksDescription.split(Protocol.Delimiter.Inner.toString());
		for (String node : nodes){
			String [] parts = node.split(Protocol.Delimiter.Message.toString(), count);
			state = new TalkState(Integer.valueOf(parts[0]),Integer.valueOf(parts[1]),Integer.valueOf(parts[2]));
			talkStates.add(state);
		}
		
	}
	
	public TalkState createTalkState(int id, int participantVersion, int messagesCount){
		return new TalkState(id, participantVersion, messagesCount);
	}

	class TalkState {
		
		private int talkID;
		private int participantsCount;
		private int messagesCount;
		
		
		public TalkState(int id, int participantVersion, int messagesCount) {
			this.talkID=id;
			this.participantsCount = participantVersion;
			this.messagesCount = messagesCount;
		}


		public int getId() {
			return talkID;
		}


		public int getParticipantsCount() {
			return participantsCount;
		}


		public int getMessagesCount() {
			return messagesCount;
		}
	}

	public int getContactsCount() {
		return contactsCount;
	}

	public Collection<TalkState> getTalkStates() {
		return talkStates;
	}


	public String getName() {
		return name;
	}


	public int getTalksCount() {
		return talksCount;
	}

	
	

}
