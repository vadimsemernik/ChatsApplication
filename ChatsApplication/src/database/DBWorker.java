package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import server.logic.entities.ServerMessage;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;

public class DBWorker {
	
	private DBConnection connection;
	
	public DBWorker (String url, String user, String password){
		connection = DBConnection.getInstance(url, user, password);
	}
	
	public void addProfile(ServerProfile profile) {
		addProfileToProfilesTable(profile);
		createContactsTable(profile.getName(), profile.getContacts());
		createTalkIDsTable(profile.getName(), profile.getTalksID());
	}

	public void addTalk(ServerTalk talk) {
		addTalkToTalksTable(talk);
		createParticipantsTable(talk.getId(), talk.getParticipants());
		createMessagesTable(talk.getId(), talk.getMessages());
		
	}

	private void addTalkToTalksTable(ServerTalk talk) {
		StringBuilder builder = new StringBuilder();
		builder.append("Insert into " + QueryLibrary.Talks.Table
				+ " Values ( ");
		builder.append(talk.getId()+", ");
		builder.append("'"+talk.getTitle()+"', ");
		builder.append("'"+talk.getId()+QueryLibrary.Talks.ParticipantsTable+"', ");
		builder.append(talk.getParticipantsCount()+", ");
		builder.append("'"+talk.getId()+QueryLibrary.Talks.MessagesTable+"', ");
		builder.append(talk.getMessagesCount()+");");
		connection.changeData(builder.toString());
		
	}

	private void createParticipantsTable(int talkID, Collection<TalkParticipant> collection) {
		String tableName = String.valueOf(talkID) + QueryLibrary.Talks.ParticipantsTable;
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+tableName +" (");
		builder.append(QueryLibrary.Talks.Number + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Talks.Participant + " VARCHAR(30) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Talks.Number+"));");
		connection.changeData(builder.toString());
		if (collection != null){
			addParticipants(collection, tableName);
		}
	}

	private void createMessagesTable(int talkID, Collection<ServerMessage> collection) {
		String tableName = String.valueOf(talkID) + QueryLibrary.Talks.MessagesTable;
		StringBuilder query = new StringBuilder();
		query.append("Create table "+tableName +" (");
		query.append(QueryLibrary.Talks.Number + " INT(11) NOT NULL, ");
		query.append(QueryLibrary.Talks.Author + " VARCHAR(30) NOT NULL, ");
		query.append(QueryLibrary.Talks.Content + " VARCHAR(255) NOT NULL, ");
		query.append("Primary key ("+ QueryLibrary.Talks.Number+"));");
		connection.changeData(query.toString());
		if (collection != null) {
			addMessages(collection, tableName);
		}
	}
	
	public void addParticipants(Collection<TalkParticipant> collection, String tableName2){
		String tableName = String.valueOf(tableName2) + QueryLibrary.Talks.MessagesTable;
		addParticipants(collection, tableName);
	}
	
	public void addParticipantToTalk(TalkParticipant participant, int talkID){
		String tableName = String.valueOf(talkID) + QueryLibrary.Talks.MessagesTable;
		addContact(participant, tableName);
	}
		
	
	private void addParticipants(List<TalkParticipant> participants, String tableName) {
		for (TalkParticipant participant :participants){
			addContact(participant, tableName);
		}
	}


	public void addMessages(Collection<ServerMessage> collection, String tableName2){
		String tableName = String.valueOf(tableName2)+QueryLibrary.Talks.MessagesTable;
		addMessages(collection, tableName);
	}
	
	private void addMessages(List<ServerMessage>messages, String tableName){
		for (ServerMessage message : messages){
			addMessage(message, tableName);
		}
	}

	private void addMessage(ServerMessage message, String tableName) {
		StringBuilder query = new StringBuilder();
		query.append("Insert into " + tableName
				+ " Values ( ");
		query.append(message.getNumber()+", ");
		query.append("'"+message.getAuthor()+"', ");
		String content = message.getContent();
		content = content.replaceAll("'", "''");				// for correct MySQL syntax
		query.append("'"+content+"'); ");
		System.out.println(query.toString());
		connection.changeData(query.toString());
		
	}


	public void addMessage(ServerMessage message, int talkID) {
		String tableName = String.valueOf(talkID)+QueryLibrary.Talks.MessagesTable;
		addMessage(message, tableName);	
	}



	private void addProfileToProfilesTable(ServerProfile profile) {
		StringBuilder builder = new StringBuilder();
		builder.append("Insert into " + QueryLibrary.Profiles.Table
				+ " Values ( ");
		builder.append("'"+profile.getName()+"', ");
		builder.append("'"+profile.getPassword()+"', ");
		builder.append("'"+profile.getName()+QueryLibrary.Profiles.ContactsTable+"', ");
		builder.append(profile.getContactsCount()+", ");
		builder.append("'"+profile.getName()+QueryLibrary.Profiles.TalksIDTable+"', ");
		builder.append(profile.getTalksCount()+");");
		connection.changeData(builder.toString());
		
	}

	private void createContactsTable(String clientName, Collection<TalkParticipant> collection) {
		String tableName = clientName + QueryLibrary.Profiles.ContactsTable;
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+tableName +" (");
		builder.append(QueryLibrary.Profiles.Number + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.Contact + " VARCHAR(30) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Profiles.Number+"));");
		connection.changeData(builder.toString());
		if (collection != null){
			addParticipants(collection, tableName);
		}
	}
	
	public void addContactsToClient(List <TalkParticipant> contacts, String clientName){
		String tableName = clientName + QueryLibrary.Profiles.ContactsTable;
		addParticipants(contacts, tableName);
	}
	
	public void addContactToClient(TalkParticipant contact, String clientName){
		String tableName = clientName + QueryLibrary.Profiles.ContactsTable;
		addContact(contact, tableName);
	}
	

	private void addContact(TalkParticipant contact, String tableName) {
		StringBuilder builder = new StringBuilder();
		builder.append("Insert into " + tableName
				+ " Values ( ");
		builder.append(contact.getNumber()+", ");
		builder.append("'"+contact.getName()+"'); ");
		connection.changeData(builder.toString());
		
	}

	private void createTalkIDsTable(String clientName, Collection<TalkID> collection) {
		String tableName = clientName + QueryLibrary.Profiles.TalksIDTable;
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+tableName +" (");
		builder.append(QueryLibrary.Profiles.Number + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.TalkID + " INT(11) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Profiles.Number+"));");
		connection.changeData(builder.toString());
		if (collection != null){
			addTalkIDs(collection, tableName);
		}
		
	}
	
	public void addTalkIDToProfile(String clientName, TalkID id){
		String tableName = clientName + QueryLibrary.Profiles.TalksIDTable;
		addTalkID(id, tableName);
	}
	
	public void addTalkIDsToProfile (List <TalkID> talkIDs, String clientName){
		String tableName = clientName + QueryLibrary.Profiles.TalksIDTable;
		addTalkIDs(talkIDs, tableName);
	}
	
	private void addTalkIDs(Collection<TalkID> collection, String tableName){
		for (TalkID id: collection){
			addTalkID(id, tableName);
		}
	}

	private void addTalkID(TalkID id,String tableName) {
		StringBuilder builder = new StringBuilder();
		builder.append("Insert into " + tableName
				+ " Values ( ");
		builder.append(id.getNumber()+", ");
		builder.append(id.getId()+"); ");
		connection.changeData(builder.toString());
		
	}

	
	
	//-----------------------------------------Select data from database----------------------------------------------
	
	
	public List <ServerMessage> getTalkMessages(int talkID, int number) throws SQLException{
		String tableName = String.valueOf(talkID)+QueryLibrary.Talks.MessagesTable;
		ResultSet result = selectDataWithStartNumber(tableName, number);
		List <ServerMessage> messages = new ArrayList<ServerMessage>();
		ServerMessage message;
		while(result.next()){
			message = new ServerMessage(Integer.valueOf(result.getString(1)), result.getString(2), result.getString(3));
			messages.add(message);
		}
		return messages;
	}
	
	

	public List <ServerMessage> getTalkMessages(int talkID) throws  SQLException{
		String tableName = String.valueOf(talkID)+QueryLibrary.Talks.MessagesTable;
		ResultSet result = selectData(tableName);
		List <ServerMessage> messages = new ArrayList<ServerMessage>();
		ServerMessage message;
		while(result.next()){
			message = new ServerMessage(result.getInt(1), result.getString(2), result.getString(3));
			messages.add(message);
		}
		return messages;
	}
	
	public List <TalkParticipant> getTalkParticipants(int talkID) throws SQLException{
		String tableName = String.valueOf(talkID)+QueryLibrary.Talks.ParticipantsTable;
		List <TalkParticipant> participants = getParticipants(tableName);
		return participants;
	} 
	
	public List <TalkParticipant> getTalkParticipants(int talkID, int number) throws SQLException{
		String tableName = String.valueOf(talkID)+QueryLibrary.Talks.ParticipantsTable;
		List <TalkParticipant> participants = getParticipants(tableName, number);
		return participants;
	} 
	
	public List <TalkParticipant> getProfileContacts(String profileName, int number) throws SQLException{
		String tableName = profileName+QueryLibrary.Profiles.ContactsTable;
		List <TalkParticipant> contacts = getParticipants(tableName, number);
		return contacts;
	}
	
	public List <TalkParticipant> getProfileContacts(String profileName) throws SQLException{
		String tableName = profileName+QueryLibrary.Profiles.ContactsTable;
		List <TalkParticipant> contacts = getParticipants(tableName);
		return contacts;
	} 
	
	private List<TalkParticipant> getParticipants(String tableName, int number) throws SQLException{
		ResultSet result = selectDataWithStartNumber(tableName, number);
		List <TalkParticipant> participants = new ArrayList<TalkParticipant>();
		TalkParticipant participant;
		while(result.next()){
			participant = new TalkParticipant(result.getInt(1), result.getString(2));
			participants.add(participant);
		}
		return participants;
	}
	
	private List<TalkParticipant> getParticipants(String tableName) throws SQLException{
		ResultSet result = selectData(tableName);
		List <TalkParticipant> participants = new ArrayList<TalkParticipant>();
		TalkParticipant participant;
		while(result.next()){
			participant = new TalkParticipant(result.getInt(1), result.getString(2));
			participants.add(participant);
		}
		return participants;
	}

	public List<TalkID> getProfileTalkIDs(String profileName) throws SQLException{
		String tableName = profileName+QueryLibrary.Profiles.TalksIDTable;
		ResultSet result = selectData(tableName);
		List <TalkID> ids = new ArrayList<TalkID>();
		TalkID id;
		while(result.next()){
			id = new TalkID(result.getInt(1), result.getInt(2));
			ids.add(id);
		}
		return ids;
	}
	
	public List<TalkID> getProfileTalkIDs(String profileName, int number) throws SQLException{
		String tableName = profileName+QueryLibrary.Profiles.TalksIDTable;
		ResultSet result = selectDataWithStartNumber(tableName, number);
		List <TalkID> ids = new ArrayList<TalkID>();
		TalkID id;
		while(result.next()){
			id = new TalkID(result.getInt(1), result.getInt(2));
			ids.add(id);
		}
		return ids;
	}

	public String getPassword(String profileName) throws SQLException{
		ResultSet result = selectEntityFromTable(QueryLibrary.Profiles.Table.toString(), QueryLibrary.Profiles.Password.toString(), 
				profileName, QueryLibrary.Profiles.ClientName.toString());
		result.next();
		return result.getString(1);
	}
	
	public int getContactsCount(String profileName) throws SQLException{
		ResultSet result = selectEntityFromTable(QueryLibrary.Profiles.Table.toString(), QueryLibrary.Profiles.ContactsCount.toString(), 
				profileName, QueryLibrary.Profiles.ClientName.toString());
		result.next();
		return result.getInt(1);
	}
	
	public int getTalkIDsCount(String profileName) throws SQLException{
		ResultSet result = selectEntityFromTable(QueryLibrary.Profiles.Table.toString(), QueryLibrary.Profiles.TalksIDCount.toString(), 
				profileName, QueryLibrary.Profiles.ClientName.toString());
		result.next();
		return result.getInt(1);
	}
	
	public String getTalkTitle(int talkID) throws SQLException{
		ResultSet result = selectEntityFromTable(QueryLibrary.Talks.Table.toString(), QueryLibrary.Talks.TalkTitle.toString(), 
				talkID, QueryLibrary.Talks.TalkID.toString());
		result.next();
		return result.getString(1);
	}
	
	public int getParticipantsCount(int talkID) throws SQLException{
		ResultSet result = selectEntityFromTable(QueryLibrary.Talks.Table.toString(), QueryLibrary.Talks.ParticipantsCount.toString(), 
				talkID, QueryLibrary.Talks.TalkID.toString());
		result.next();
		return result.getInt(1);
	}
	
	public int getMessagesCount(int talkID) throws SQLException{
		ResultSet result = selectEntityFromTable(QueryLibrary.Talks.Table.toString(), QueryLibrary.Talks.MessagesCount.toString(), 
				talkID, QueryLibrary.Talks.TalkID.toString());
		result.next();
		return result.getInt(1);
	}
	
	
	
	
	private ResultSet selectData(String tableName) {
		StringBuilder query = new StringBuilder();
		query.append("Select * from "+tableName+";");
		ResultSet result = connection.getDBData(query.toString());
		return result;
	}
	
	private ResultSet selectColumnFromTable (String tableName, String columnName){
		StringBuilder query = new StringBuilder();
		query.append("Select ");
		query.append(columnName);
		query.append(" from ");
		query.append(tableName+";");
		ResultSet result = connection.getDBData(query.toString());
		return result;
	}
	
	private ResultSet selectRowFromTable (String tableName, String key, String keyColumn){
		key = key.replaceAll("'", "''");					//for correct SQL syntax
		key = "'"+key+"'";
		StringBuilder query = new StringBuilder();
		query.append("Select * from "+tableName);
		query.append(" where ");
		query.append(keyColumn);
		query.append("="+key+";");
		ResultSet result = connection.getDBData(query.toString());
		return result;
	}
	
	private ResultSet selectRowFromTable (String tableName, int key, String keyColumn){
		StringBuilder query = new StringBuilder();
		query.append("Select * from "+tableName);
		query.append(" where ");
		query.append(keyColumn);
		query.append("="+key+";");
		ResultSet result = connection.getDBData(query.toString());
		return result;
	}
	
	private ResultSet selectEntityFromTable(String tableName, String columnName, String key, String keyColumn){
		key = key.replaceAll("'", "''");				//for correct SQL syntax
		key = "'"+key+"'";							
		StringBuilder query = new StringBuilder();
		query.append("Select ");
		query.append(columnName);
		query.append(" from ");
		query.append(tableName);
		query.append(" where ");
		query.append(keyColumn);
		query.append("="+key+";");
		ResultSet result = connection.getDBData(query.toString());
		return result;
	}
	
	private ResultSet selectEntityFromTable(String tableName, String columnName, int key, String keyColumn){
		StringBuilder query = new StringBuilder();
		query.append("Select ");
		query.append(columnName);
		query.append(" from ");
		query.append(tableName);
		query.append(" where ");
		query.append(keyColumn);
		query.append("="+key+";");
		ResultSet result = connection.getDBData(query.toString());
		return result;
	}
	
	private ResultSet selectDataWithStartNumber(String tableName, int number) {
		StringBuilder query = new StringBuilder();
		query.append("Select * from "+tableName);
		query.append(" where " + QueryLibrary.Talks.Number);
		query.append(">= "+number+";");
		ResultSet result = connection.getDBData(query.toString());
		return result;
	}
}
