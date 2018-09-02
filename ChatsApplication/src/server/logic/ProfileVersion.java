package server.logic;

import java.util.Collection;
import java.util.LinkedList;

import server.logic.entities.TalkID;

public class ProfileVersion {
	
	private String name;
	private Collection <TalkID> talkIDs;
	private Collection <TalkVersion> talkVersions;
	private int contactsCount;
	private int talksCount;
	
	public ProfileVersion (Collection<TalkID>talkIDs, int contactsCount){
		this.talkIDs = talkIDs;
		this.contactsCount=contactsCount;
		this.talksCount=talkIDs.size();
		talkVersions = new LinkedList<TalkVersion>();
	}
	
	public ProfileVersion (int contactsCount){
		this.contactsCount=contactsCount;
		talkVersions = new LinkedList<TalkVersion>();
	}
	
	public void addTalkIds(Collection <TalkID> talkIDs){
		this.talkIDs = talkIDs;
		this.talksCount=talkIDs.size();
	}
	
	public void addTalkVersion(int participantsCount, int messagesCount){
		TalkVersion version = new TalkVersion(participantsCount, messagesCount);
		talkVersions.add(version);
	}
	
	
	/*public Collection<TalkID> getTalkIDs() {
		return talkIDs;
	}*/

	public Collection<TalkVersion> getTalkVersions() {
		return talkVersions;
	}

	public int getContactsCount() {
		return contactsCount;
	}

	public int getTalksCount() {
		return talksCount;
	}




	private class TalkVersion {
		private int participantsCount;
		private int messagesCount;
		
		TalkVersion (int participantsCount, int messagesCount){
			this.participantsCount=participantsCount;
			this.messagesCount=messagesCount;
		}

		public int getParticipantsCount() {
			return participantsCount;
		}

		public int getMessagesCount() {
			return messagesCount;
		}
		
		
		
	}
	

}
