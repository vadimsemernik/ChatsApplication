package database;

import java.util.Collection;
import java.util.List;

import server.logic.entities.ServerMessage;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;

public class DatabaseCreator {
	
	private List <ServerProfile> profiles;
	private List <ServerTalk> talks;
	private DBConnection connection;
	
	public DatabaseCreator (TemporaryStorage storage, String url, String user, String password){
		profiles = storage.getProfiles();
		talks = storage.getTalks();
		connection = DBConnection.getInstance(url, user, password);
	}
	
	public DatabaseCreator (TemporaryStorage storage){
		profiles = storage.getProfiles();
		talks = storage.getTalks();
		connection = DBConnection.getInstance();
	}
	
	public boolean fillDatabase(){
		createInitialTables();
		addProfiles();
		addTalks();
		return true;
	}

	private void addTalks() {
		for (ServerTalk talk : talks){
			addTalk(talk);
		}
		
	}

	private void addTalk(ServerTalk talk) {
		addTalkToTalks(talk);
		createParticipantsTable(talk.getId(), talk.getParticipants());
		createMessagesTable(talk.getId(), talk.getMessages());
		
	}

	private void addTalkToTalks(ServerTalk talk) {
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

	private void createParticipantsTable(int id, Collection<TalkParticipant> collection) {
		String table = String.valueOf(id) + QueryLibrary.Talks.ParticipantsTable;
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+table +" (");
		builder.append(QueryLibrary.Talks.Number + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Talks.Participant + " VARCHAR(30) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Talks.Number+"));");
		connection.changeData(builder.toString());
		for (TalkParticipant participant : collection){
			addContact(participant, table);
		}
		
	}

	private void createMessagesTable(int id, Collection<ServerMessage> collection) {
		String table = String.valueOf(id) + QueryLibrary.Talks.MessagesTable;
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+table +" (");
		builder.append(QueryLibrary.Talks.Number + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Talks.Author + " VARCHAR(30) NOT NULL, ");
		builder.append(QueryLibrary.Talks.Content + " VARCHAR(255) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Talks.Number+"));");
		connection.changeData(builder.toString());
		for (ServerMessage message : collection){
			addMessage(message, table);
		}
		
	}

	private void addMessage(ServerMessage message, String table) {
		StringBuilder builder = new StringBuilder();
		builder.append("Insert into " + table
				+ " Values ( ");
		builder.append(message.getNumber()+", ");
		builder.append("'"+message.getAuthor()+"', ");
		String content = message.getContent();
		content = content.replaceAll("'", "''");				// for correct MySQL syntax
		builder.append("'"+content+"'); ");
		System.out.println(builder.toString());
		connection.changeData(builder.toString());
		
	}

	private void addProfiles() {
		for (ServerProfile profile : profiles){
			addProfile(profile);
		}
		
	}

	private void addProfile(ServerProfile profile) {
		addProfileToProfiles(profile);
		createContactsTable(profile.getName(), profile.getContacts());
		createTalksIDTable(profile.getName(), profile.getTalksID());
		
		
	}

	private void addProfileToProfiles(ServerProfile profile) {
		StringBuilder builder = new StringBuilder();
		builder.append("Insert into " + QueryLibrary.Profiles.Table
				+ " Values ( ");
		builder.append("'"+profile.getName()+"', ");
		builder.append("'"+profile.getPassword()+"', ");
		builder.append("'"+profile.getName()+QueryLibrary.Profiles.ContactsTable+"', ");
		builder.append(profile.getContactsCount()+", ");
		builder.append("'"+profile.getName()+QueryLibrary.Profiles.TalksIDTable+"', ");
		builder.append(profile.getTalksCount()+");");
		System.out.println(builder.toString());
		connection.changeData(builder.toString());
		
	}

	private void createContactsTable(String clientName, Collection<TalkParticipant> collection) {
		String table = clientName + QueryLibrary.Profiles.ContactsTable;
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+table +" (");
		builder.append(QueryLibrary.Profiles.Number + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.Contact + " VARCHAR(30) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Profiles.Number+"));");
		connection.changeData(builder.toString());
		for (TalkParticipant contact : collection){
			addContact(contact, table);
		}
		
	}

	private void addContact(TalkParticipant contact, String table) {
		StringBuilder builder = new StringBuilder();
		builder.append("Insert into " + table
				+ " Values ( ");
		builder.append(contact.getNumber()+", ");
		builder.append("'"+contact.getName()+"'); ");
		connection.changeData(builder.toString());
		
	}

	private void createTalksIDTable(String clientName, Collection<TalkID> collection) {
		String table = clientName + QueryLibrary.Profiles.TalksIDTable;
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+table +" (");
		builder.append(QueryLibrary.Profiles.Number + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.TalkID + " INT(11) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Profiles.Number+"));");
		connection.changeData(builder.toString());
		for (TalkID id: collection){
			addTalkID(table, id);
		}
		
	}

	private void addTalkID(String table, TalkID id) {
		StringBuilder builder = new StringBuilder();
		builder.append("Insert into " + table
				+ " Values ( ");
		builder.append(id.getNumber()+", ");
		builder.append(id.getId()+"); ");
		connection.changeData(builder.toString());
		
	}

	private void createInitialTables() {
		createProfileTable();
		createTalksTable();
		
	}

	private void createProfileTable() {
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+QueryLibrary.Profiles.Table+" (");
		builder.append(QueryLibrary.Profiles.ClientName + " VARCHAR(30) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.Password + " VARCHAR(30) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.ContactsTable + " VARCHAR(50) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.ContactsCount + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.TalksIDTable + " VARCHAR(50) NOT NULL, ");
		builder.append(QueryLibrary.Profiles.TalksIDCount + " INT(11) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Profiles.ClientName+"));");
		connection.changeData(builder.toString());

		
	}

	private void createTalksTable() {
		StringBuilder builder = new StringBuilder();
		builder.append("Create table "+QueryLibrary.Talks.Table+" (");
		builder.append(QueryLibrary.Talks.TalkID + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Talks.TalkTitle + " VARCHAR(100) NOT NULL, ");
		builder.append(QueryLibrary.Talks.ParticipantsTable + " VARCHAR(120) NOT NULL, ");
		builder.append(QueryLibrary.Talks.ParticipantsCount + " INT(11) NOT NULL, ");
		builder.append(QueryLibrary.Talks.MessagesTable + " VARCHAR(120) NOT NULL, ");
		builder.append(QueryLibrary.Talks.MessagesCount+ " INT(11) NOT NULL, ");
		builder.append("Primary key ("+ QueryLibrary.Talks.TalkID+"));");
		connection.changeData(builder.toString());
		
	}

}
