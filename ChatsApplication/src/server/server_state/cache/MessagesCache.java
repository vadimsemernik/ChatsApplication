package server.server_state.cache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import server.data.ApplicationDatabase;
import server.logic.entities.ServerMessage;
import server.logic.entities.cache_entities.CacheMessages;

public class MessagesCache {
	
	private ApplicationDatabase database;
	private Map <Integer, CacheMessages> map;

	public MessagesCache(ApplicationDatabase database) {
		this.database=database;
		map = new HashMap<Integer, CacheMessages>();
	}

	public void saveAllToDatabase() {
		// TODO Auto-generated method stub
		
	}

	public void addMessage(ServerMessage message, int talkID) {
		CacheMessages talkMessages = map.get(talkID);
		if (talkMessages==null){
			talkMessages = new CacheMessages(talkID, database);
			map.put(talkID, talkMessages);
		}
		talkMessages.addMessage(message);
	}

	public Collection<ServerMessage> getMessages(int talkID, int from) {
		Collection<ServerMessage> result=null;
		CacheMessages messages = map.get(talkID);
		if (messages==null){
			try {
				result = database.getTalkMessages(talkID, from);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			result = messages.getMessages(from);
		}
		return result;
	}

	public Collection<ServerMessage> getMessages(int talkID) {
		Collection<ServerMessage> result=null;
		CacheMessages messages = map.get(talkID);
		if (messages==null){
			try {
				result = database.getTalkMessages(talkID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			result = messages.getMessages();
		}
		return result;
	}

}
