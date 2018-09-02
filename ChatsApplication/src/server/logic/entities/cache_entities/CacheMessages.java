package server.logic.entities.cache_entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

import server.data.ApplicationDatabase;
import server.logic.entities.ServerMessage;
import server.logic.entities.comparators.ServerMessageComparator;


public class CacheMessages {
	
	private SortedSet <ServerMessage> messages;
	private boolean dirty=false;
	private int position;
	
	private ApplicationDatabase database;
	private int talkID;
	
	public CacheMessages(int talkID, ApplicationDatabase database, ServerMessage message){
		messages = new TreeSet<ServerMessage>(new ServerMessageComparator());
		messages.add(message);
		position=message.getNumber();
		dirty=true;
		this.database=database;
		this.talkID=talkID;
	}
	
	public CacheMessages(int talkID,ApplicationDatabase database){
		messages = new TreeSet<ServerMessage>(new ServerMessageComparator());
		this.database=database;
		this.talkID=talkID;
	}
	
	public void addMessage(ServerMessage message){
		messages.add(message);
		if (!dirty){
			position=message.getNumber();
			dirty=true;
		}
	}

	public Collection<ServerMessage> getMessages() {
		if (position == 1) return messages;				// all talk messages are in cache now
		Collection<ServerMessage> result = new LinkedList<ServerMessage>();
		try {
			result = database.getTalkMessagesInBounds(talkID, 1, position-1);
			result.addAll(messages);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isDirty() {
		return dirty;
	}

	public int getPosition() {
		return position;
	}

	public Collection<ServerMessage> getMessages(int from) {
		Collection<ServerMessage> result = new LinkedList<ServerMessage>();
		if (from>=position){
			result = getMessagesFromPosition(from-position);
		} else {
			try {
				result = database.getTalkMessagesInBounds(talkID, from, position-1);
				result.addAll(messages);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private Collection<ServerMessage> getMessagesFromPosition(int start) {
		Collection<ServerMessage> result = new LinkedList<ServerMessage>();
		ArrayList<ServerMessage> list = new ArrayList<ServerMessage>(messages);
		for(int i = start;i<list.size();i++){
			result.add(list.get(i));
		}
		return result;
	}
	
	
	

}
