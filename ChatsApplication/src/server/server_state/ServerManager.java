package server.server_state;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import server.clients.ClientsManager;
import server.data.ApplicationDatabase;
import server.logic.ProfileVersion;
import server.logic.entities.ServerMessage;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;
import server.server_state.cache.MessagesCache;
import server.server_state.cache.ParticipantsCache;

public class ServerManager {
	
	private static ServerManager instance;
	
	private ApplicationDatabase database;
	private MessagesCache messagesCache;
	private ParticipantsCache participantsCache;
	
	
	ServerManager(ApplicationDatabase database) {
		if (database == null){
			System.out.println("database is null");
		}
		this.database = database;
		System.out.println("Initializing ServerManager");
		messagesCache = new MessagesCache(database);
		participantsCache = new ParticipantsCache(database);
		
	}


	public static ServerManager getInstance(ApplicationDatabase database){
		if (instance != null){
			instance.cleanInstance();
		}
		instance = new ServerManager(database);
		return instance;
	}


	private void cleanInstance() {
		if (messagesCache!=null){
			messagesCache.saveAllToDatabase();
			participantsCache.saveAllToDatabase();
		}
		
	}


	public static void addNewMessageToTalk(ServerMessage message, int talkID) {
		instance.addMessage(message, talkID);
	}
	
	private void addMessage(ServerMessage message, int talkID) {
		messagesCache.addMessage(message, talkID);
		Collection <TalkParticipant> participants = participantsCache.getParticipants(talkID); 
		database.addMessage(message, talkID);
		ServerStateFactory.getServer().receiveTalkMessage(talkID, message, participants);
	}


	public static void addParticipant(TalkParticipant participant, int talkID){
		instance.database.addParticipantToTalk(participant, talkID);
	}
	
	public static void addContact(TalkParticipant contact, String profileName){
		instance.database.addContactToClient(contact, profileName);
	}
	
	public static void addTalkID(TalkID id, String profileName){
		instance.database.addTalkIDToProfile(profileName, id);
	}


	public static Collection<ServerMessage> getMessages(int talkID, int from) {
		return instance.getTalkMessages(talkID, from);
	}


	private Collection<ServerMessage> getTalkMessages(int talkID, int from) {
		return messagesCache.getMessages(talkID, from);
	}


	public static Collection<TalkParticipant> getParticipants(int talkID, int from) {
		return instance.getTalkParticipants(talkID, from);
	}


	private Collection<TalkParticipant> getTalkParticipants(int talkID, int from) {
		return participantsCache.getParticipants(talkID, from);
	}


	public static ServerProfile loadProfile(String name) {
		ServerProfile profile=null;
		try {
			profile = instance.loadFullProfile(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profile;
	}


	private ServerProfile loadFullProfile(String name) throws SQLException {
		Collection<TalkParticipant> contacts= database.getProfileContacts(name);
		Collection<TalkID>talkIDs = database.getProfileTalkIDs(name);
		String password = database.getPassword(name);
		ServerProfile profile = new ServerProfile(name, password, contacts, talkIDs);
		return profile;
	}


	public static Collection<ServerTalk> getTalks(Collection<TalkID> talkIDs) {
		Collection<ServerTalk> talks=null;
		try {
			talks = instance.getFullTalks(talkIDs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return talks;
	}


	private Collection<ServerTalk> getFullTalks(Collection<TalkID> talkIDs) throws SQLException {
		Collection <ServerTalk> talks = new LinkedList<ServerTalk>();
		ServerTalk talk;
		Collection<TalkParticipant> participants;
		String talkTitle;
		Collection<ServerMessage> messages;
		for (TalkID id : talkIDs){
			talkTitle = database.getTalkTitle(id.getId());
			participants = participantsCache.getParticipants(id.getId());
			messages = messagesCache.getMessages(id.getId());
			talk = new ServerTalk(talkTitle, id.getId(), participants, messages);
			talks.add(talk);
		}
		return talks;
	}


	public static Collection<TalkParticipant> getContacts(String profileName, int from) {
		Collection <TalkParticipant> contacts = null;
		try {
			contacts = instance.getContactsFromPosition(profileName, from);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contacts;
	}


	private Collection<TalkParticipant> getContactsFromPosition(String profileName, int from) throws SQLException {
		return database.getProfileContacts(profileName, from);
	}


	public static Collection<TalkID> getNewTalksIDs(String profileName, int from) {
		Collection <TalkID> talkIDs = null;
		try {
			talkIDs = instance.getTalksIDs(profileName, from);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return talkIDs;
	}


	private Collection<TalkID> getTalksIDs(String profileName, int from) throws SQLException {
		return database.getProfileTalkIDs(profileName, from);
	}


	
	

}
