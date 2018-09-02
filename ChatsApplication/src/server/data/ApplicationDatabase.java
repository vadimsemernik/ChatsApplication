package server.data;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import server.logic.entities.ServerMessage;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;
import server.logic.entities.cache_entities.CacheProfile;
import server.logic.entities.cache_entities.CacheTalk;

public interface ApplicationDatabase {
	
	public void addProfile(ServerProfile profile);
	public void addTalk(ServerTalk talk);
	public void addParticipants(Collection<TalkParticipant>participants, int talkID);
	public void addParticipantToTalk(TalkParticipant participant, int talkID);
	public void addMessages(Collection<ServerMessage>messages, int talkID);
	public void addMessage(ServerMessage message, int talkID);
	public void addContactsToClient(Collection <TalkParticipant> contacts, String clientName);
	public void addContactToClient(TalkParticipant contact, String clientName);
	public void addTalkIDToProfile(String clientName, TalkID id);
	public void addTalkIDsToProfile (Collection <TalkID> talkIDs, String clientName);
	
	public Collection <ServerMessage> getTalkMessages(int talkID, int number) throws SQLException;
	public Collection <ServerMessage> getTalkMessages(int talkID) throws  SQLException;
	public Collection <TalkParticipant> getTalkParticipants(int talkID) throws SQLException;
	public Collection <TalkParticipant> getTalkParticipants(int talkID, int number)throws SQLException;
	public Collection <TalkParticipant> getProfileContacts(String profileName, int number) throws SQLException;
	public Collection <TalkParticipant> getProfileContacts(String profileName) throws SQLException;
	public Collection<TalkID> getProfileTalkIDs(String profileName) throws SQLException;
	public Collection<TalkID> getProfileTalkIDs(String profileName, int number) throws SQLException;
	public String getPassword(String profileName) throws SQLException;
	public int getContactsCount(String profileName) throws SQLException;
	public int getTalkIDsCount(String profileName) throws SQLException;
	public String getTalkTitle(int talkID) throws SQLException;
	public int getParticipantsCount(int talkID) throws SQLException;
	public int getMessagesCount(int talkID) throws SQLException;
	
	public Collection <CacheProfile> getCacheProfiles() throws SQLException;
	public Collection <CacheTalk> getCacheTalks() throws SQLException;
	public Collection<ServerMessage> getTalkMessagesInBounds(int talkID, int floor, int ceiling)throws SQLException;
	
	
	
	
	

}
