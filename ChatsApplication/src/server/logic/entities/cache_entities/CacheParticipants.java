package server.logic.entities.cache_entities;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import server.logic.entities.TalkParticipant;
import server.logic.entities.comparators.TalkParticipantComparator;

public class CacheParticipants {

	private SortedSet <TalkParticipant> participants;
	private boolean dirty=false;
	private int position;
	
	/*
	 * if creating an instance with participants list from database
	 * dirty remains false
	 */
	public CacheParticipants (Collection<TalkParticipant> collection, boolean fromDatabase){
		participants = new TreeSet<TalkParticipant>(new TalkParticipantComparator());
		for (TalkParticipant participant:collection){
			participants.add(participant);
		}
		if (!fromDatabase){
			dirty=true;
			position = participants.first().getNumber();
		}
	}
	
	public void addParticipant(TalkParticipant participant){
		participants.add(participant);
		if (!dirty){
			dirty=true;
			position=participant.getNumber();
		}
	}
	
	
	
	
	public SortedSet<TalkParticipant> getParticipants() {
		return participants;
	}
	public boolean isDirty() {
		return dirty;
	}
	public int getPosition() {
		return position;
	}
	
	
	
}
