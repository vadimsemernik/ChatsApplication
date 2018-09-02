package server.logic.entities.cache_entities;

import java.util.Collection;
import java.util.List;

import java.util.concurrent.locks.ReentrantLock;


import server.logic.entities.ServerProfile;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;
import server.server_state.ServerManager;

public class CacheProfile {
	
	private String name;
	private String password;
	private volatile int contactsCount;
	private volatile int talksCount;
	private byte dirty = 0;
	private ReentrantLock lock = new ReentrantLock();
	
	
	

	public CacheProfile(String name, String password, List<TalkParticipant> contacts, List<TalkID>talkIDs) {
		this.name = name;
		this.password = password;
		contactsCount=contacts.size();
		talksCount = talkIDs.size();
	}
	
	public CacheProfile(String name, String password, int contactsCount, int talksCount) {
		this.name = name;
		this.password = password;
		this.contactsCount=contactsCount;
		this.talksCount = talksCount;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof CacheProfile){
			CacheProfile profile = (CacheProfile) obj;
			return name.equals(profile.name)
					&&password.equals(profile.getPassword())
					&&contactsCount==profile.contactsCount
					&&talksCount==profile.talksCount;
		}
		return false;
	}
	
	
	@Override
	public int hashCode(){
		int result = 21;
		result = 31*result + name.hashCode();
		result = 31*result + password.hashCode();
		result = 31*result + talksCount+ contactsCount;
		return result;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getContactsCount() {
		return contactsCount;
	}

	public int getTalksCount() {
		return talksCount;
	}

	public void setContactsCount(int count) {
		contactsCount=count;
	}

	public void setTalksCount(int count) {
		talksCount=count;
	}
	
	
	
	public byte getDirty() {
		return dirty;
	}

	public void setDirty(byte dirty) {
		this.dirty = dirty;
	}

	public ServerProfile loadFullProfile() {
		lock.lock();
		try{
			ServerProfile profile;
			saveChangesToDatabase();
			profile = ServerManager.loadProfile(name);
			return profile;
		} finally{
			lock.unlock();
		}
		
	}

	private void saveChangesToDatabase() {
		// TODO Auto-generated method stub
		
	}
	
	public void lock(){
		lock.lock();
	}
	
	public void unlock(){
		lock.unlock();
	}

	public Collection<TalkParticipant> getContacts(int from) {
		return ServerManager.getContacts(name, from);
	}

	public Collection<TalkID> getNewTalkIDs(int from) {
		return ServerManager.getNewTalksIDs(name, from);
	}
	
	

}
