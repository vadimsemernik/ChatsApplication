package server.logic.entities.cache_entities;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import server.logic.entities.ServerMessage;
import server.logic.entities.ServerTalk;
import server.logic.entities.TalkParticipant;
import server.server_state.ServerManager;


public class CacheTalk {

	private String title;
	private int talkID;
	private volatile int participantsCount;
	private volatile int messagesCount; 
	private byte dirty = 0;
	
	private ReentrantLock lock = new ReentrantLock();
	
	
	

	
	public CacheTalk(int id, String title,  List<TalkParticipant> participants, List<ServerMessage> messages) {
		this.title = title;
		this.talkID = id;
		participantsCount = participants.size();
		messagesCount =messages.size(); 
		
	}
	
	public CacheTalk(int id, String title,  int participantsCount, int messagesCount) {
		this.title = title;
		this.talkID = id;
		this.participantsCount = participantsCount;
		this.messagesCount =messagesCount; 	
	}
	
	
	
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof CacheTalk){
			CacheTalk talk = (CacheTalk) obj;
			return title.equals(talk.title)
					&&talkID==talk.getId()
					&&participantsCount==talk.participantsCount
					&&messagesCount==talk.messagesCount;
		}
		return false;
	}
	
	
	@Override
	public int hashCode(){
		int result = 21;
		result = 31*result + title.hashCode();
		result = 31*result + talkID;
		result = 31*result + messagesCount+ participantsCount;
		return result;
	}

	public String getTitle() {
		return title;
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
	
	
	
	


	public byte getDirty() {
		return dirty;
	}

	public void setDirty(byte dirty) {
		this.dirty = dirty;
	}

	public void addMessage(String author, String content) {
		lock.lock();
		try {
			ServerMessage message = new ServerMessage(++messagesCount, author, content);
			ServerManager.addNewMessageToTalk(message, talkID);
		} finally{
			lock.unlock();
		}
		
		
	}
	
	public void lock(){
		lock.lock();
	}
	
	public void unlock(){
		lock.unlock();
	}

	public Collection<ServerMessage> getMessages(int from) {
		if (from>messagesCount){
			return null;
		}
		return ServerManager.getMessages(talkID, from);
	}

	public Collection<TalkParticipant> getParticipants(int from) {
		if (from>participantsCount){
			return null;
		}
		return ServerManager.getParticipants(talkID, from);
	}
	
	
	
	
}
