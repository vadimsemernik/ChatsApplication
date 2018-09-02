package server.logic.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import server.logic.entities.cache_entities.CacheProfile;

public class ServerProfile {
	
	private String name;
	private String password;
	private Collection<TalkParticipant> contacts;
	private Collection<TalkID> talksID;
	private int contactsCount;
	private int talksCount;
	
	
	

	public ServerProfile(String name, String password, Collection<TalkParticipant> contacts, Collection<TalkID>talksID) {
		this.name = name;
		this.password = password;
		this.contacts = contacts;
		this.talksID=talksID;
		contactsCount=contacts.size();
		talksCount = talksID.size();
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof ServerProfile){
			ServerProfile profile = (ServerProfile) obj;
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


	public void addTalkID(TalkID id){
		talksID.add(id);
	}
	
	public String getName() {
		return name;
	}


	public String getPassword() {
		return password;
	}


	public Collection<TalkParticipant> getContacts() {
		return contacts;
	}


	public Collection<TalkID> getTalksID() {
		return talksID;
	}


	public int getContactsCount() {
		return contactsCount;
	}


	public int getTalksCount() {
		return talksCount;
	}	
	
	
	
	
	

}
