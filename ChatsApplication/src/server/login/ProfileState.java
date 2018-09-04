package server.login;

import java.util.Collection;
import java.util.LinkedList;

import server.protocol.Protocol;

public class ProfileState {
	
	private String name;
	private int contactsCount;
	private Collection <TalkState> talkStates;
	private int talksCount;
	
	
	public static ProfileState createInitialProfileState(String name, int contactsCount, String talksDescription) {
		ProfileState state = new ProfileState(name,contactsCount,talksDescription);
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
	
	public void addTalkState(String title, int talkID, int participantsCount, int messagesCount) {
		TalkState state = new TalkState(title, talkID, participantsCount, messagesCount);
		talkStates.add(state);
		
	}
	
	private void fillTalks(String talksDescription) {
		talkStates = new LinkedList<TalkState>();
		TalkState state;
		int count = Integer.valueOf(Protocol.Size.TalkDescription.toString());
		String [] nodes = talksDescription.split(Protocol.Delimiter.Inner.toString());
		for (String node : nodes){
			String [] parts = node.split(Protocol.Delimiter.Message.toString(), count);
			state = new TalkState(parts[0], Integer.valueOf(parts[1]),Integer.valueOf(parts[2]),Integer.valueOf(parts[3]));
			talkStates.add(state);
		}
		
	}
	
	public TalkState createTalkState(String title, int id, int participantVersion, int messagesCount){
		return new TalkState(title, id, participantVersion, messagesCount);
	}

	class TalkState {
		
		private int talkID;
		private int participantsCount;
		private int messagesCount;
		private String title;
		
		
		public TalkState(String title, int id, int participantCount, int messagesCount) {
			this.title=title;
			this.talkID=id;
			this.participantsCount = participantCount;
			this.messagesCount = messagesCount;
		}

		
		
		public String getTitle() {
			return title;
		}



		public void setTitle(String title) {
			this.title = title;
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


		public void setParticipantsCount(int participantsCount) {
			this.participantsCount = participantsCount;
		}


		public void setMessagesCount(int messagesCount) {
			this.messagesCount = messagesCount;
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
