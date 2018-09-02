package server.server_state.cache;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.data.ApplicationDatabase;
import server.logic.entities.ServerMessage;
import server.logic.entities.TalkParticipant;
import server.logic.entities.cache_entities.CacheParticipants;

public class ParticipantsCache {
	
	private ApplicationDatabase database;
	private Map <Integer, CacheParticipants> map;

	public ParticipantsCache(ApplicationDatabase database) {
		this.database = database;
		map = new HashMap <Integer, CacheParticipants>();
	}

	public void saveAllToDatabase() {
		// TODO Auto-generated method stub
		
	}

	public Collection<TalkParticipant> getParticipants(int talkID) {
		CacheParticipants participants = map.get(talkID);
		if (participants==null){
			try {
				participants = new CacheParticipants(database.getTalkParticipants(talkID), true);
				map.put(talkID, participants);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return participants.getParticipants();
	}

	public Collection<TalkParticipant> getParticipants(int talkID, int from) {
		CacheParticipants participants = map.get(talkID);
		if (participants==null){
			try {
				participants = new CacheParticipants(database.getTalkParticipants(talkID), true);
				map.put(talkID, participants);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return participants.getParticipants();
	}

}
