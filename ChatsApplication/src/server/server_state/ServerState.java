 package server.server_state;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import server.clients.ClientsManager;
import server.data.ApplicationDatabase;
import server.logic.entities.ServerMessage;
import server.logic.entities.TalkParticipant;
import server.logic.entities.cache_entities.CacheProfile;
import server.logic.entities.cache_entities.CacheTalk;
import server.logic.messageParsers.MessageParsingExecutor;
import server.logic.stateUpdates.ServerStateUpdaterExecutor;
import server.login.Accounts;



public class ServerState {
	
	private ApplicationDatabase database;
	private ServerManager manager;
	private Map <String, CacheProfile> profiles = new HashMap<String, CacheProfile>();
	private Map <Integer, CacheTalk> talks = new HashMap<Integer, CacheTalk>();
	private ServerAssistant assistant;

	public ServerState(ApplicationDatabase database) {
		this.database = database;
		getProfilesFromDatabase();
		Accounts.setAccounts(profiles);
		getTalksFromDatabase();
		MessageParsingExecutor.initExecutor();
		ServerStateUpdaterExecutor.initExecutor();
		assistant = new ServerAssistant(); 
		manager = ServerManager.getInstance(database);
	}

	

	private void getTalksFromDatabase() {
		Collection <CacheTalk> cacheTalks = null;
		try {
			cacheTalks = database.getCacheTalks();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (CacheTalk talk : cacheTalks){
			talks.put(talk.getId(), talk);
		}
	}



	private void getProfilesFromDatabase() {
		Collection <CacheProfile> list = null;
		try {
			list = database.getCacheProfiles();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (CacheProfile profile : list){
			profiles.put(profile.getName(), profile);
		}
	}



	public void receiveTalkMessage(int talkID, ServerMessage message, Collection<TalkParticipant> participants) {
		String text = assistant.createTalkMessageMessage(talkID, message);
		ClientsManager.sendMessageToConnectedClients(text, participants);
	}



	public ApplicationDatabase getDatabase() {
		return database;
	}



	public Map<String, CacheProfile> getProfiles() {
		return profiles;
	}



	public Map<Integer, CacheTalk> getTalks() {
		return talks;
	}



	public Collection<ServerMessage> getMessages(int talkID, int from) {
		CacheTalk talk = talks.get(talkID);
		if (talk == null){
			return null;
		} else {
			return talk.getMessages(from);
		}
	}



	public Collection<TalkParticipant> getParticipants(int talkID, int from) {
		CacheTalk talk = talks.get(talkID);
		if (talk == null){
			return null;
		}
		return talk.getParticipants(from);
	}
	
	
	

	

}
