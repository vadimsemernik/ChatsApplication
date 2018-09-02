package server.login;


import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;

import server.clients.ClientsManager;
import server.clients.connections.CleanConnection;
import server.logic.entities.ServerMessage;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;
import server.logic.entities.cache_entities.CacheProfile;
import server.logic.entities.cache_entities.CacheTalk;
import server.login.ProfileState.TalkState;
import server.protocol.MessageBuilderFactory;
import server.protocol.Protocol;
import server.protocol.ProtocolReader;
import server.protocol.ProtocolWriter;
import server.protocol.ServerMessageBuilder;
import server.server_state.ServerManager;
import server.server_state.ServerState;
import server.server_state.ServerStateFactory;


public class ProfileSender {
	private static ServerState server;
	private ServerMessageBuilder messageBuilder;
	private ProfileState previousState;
	private Socket socket;
	private boolean updateProfile=false;
	private ProtocolReader reader;
	private ProtocolWriter writer;
	
	public ProfileSender(ProfileState initialState, Socket socket, ProtocolWriter writer, ProtocolReader reader){
		this.socket = socket;
		this.writer=writer;
		this.reader = reader;
		if (initialState!=null){
		previousState=initialState;
		updateProfile=true;
		}
		server = ServerStateFactory.getServer();
		messageBuilder = MessageBuilderFactory.getMessageBuilder();
	}

	
	public boolean sendProfile(String clientProfile) {
		if (updateProfile){
			return updateClientProfile(clientProfile);
		} else {
			return loadProfile(clientProfile);
		}
		
	}


	private boolean updateClientProfile(String clientProfile) {
		boolean result;
		if (previousState == null){
			throw new IllegalArgumentException();
		}
		result = loadProfileUpdates(previousState);
		try {
			writer.writeMessage(Protocol.SERVICE_HEADER+Protocol.Delimiter.Message+Protocol.Message.End);
			getClientConfirmation();
		} catch (IOException e) {
			e.printStackTrace();
		}
		addConnectionAndUpdate(previousState);
		return result;
	}
	

	private boolean getClientConfirmation() {
		try {
			String confirmation = reader.readMessage();
			if (confirmation != null 
					&& confirmation.equals(Protocol.SERVICE_HEADER
							+Protocol.Delimiter.Message
							+Protocol.Message.OK)){
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}


	private boolean loadProfileUpdates(ProfileState state) {
		CacheProfile profile = server.getProfiles().get(state.getName());
		updateContacts(state.getContactsCount(), profile);
		updateTalks(state.getTalkStates(), profile);
		return true;
	}


	private void updateTalks(Collection<TalkState> talkStates, CacheProfile profile) {
		for (TalkState state : talkStates){
			CacheTalk talk = server.getTalks().get(state.getId());
			Collection <ServerMessage> messages=null;
			Collection <TalkParticipant> participants=null;
			if (talk.getMessagesCount()>state.getMessagesCount()){
				messages = talk.getMessages(state.getMessagesCount()+1);
			}
			if (talk.getParticipantsCount()> state.getParticipantsCount()){
				participants = talk.getParticipants(state.getParticipantsCount()+1);
			}
			String message = messageBuilder.getTalkUpdateMessage(talk.getId(), talk.getTitle(), participants, messages);
			try {
				writer.writeMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	private void updateContacts(int contactsCount, CacheProfile profile) {
		if (profile.getContactsCount()>contactsCount){
			Collection <TalkParticipant> newContacts = profile.getContacts(contactsCount+1);
			String message = messageBuilder.getUpdateContactsMessage(newContacts);
			try {
				writer.writeMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	private boolean loadProfile(String clientProfile) {
		try {
			CacheProfile cacheProfile = server.getProfiles().get(clientProfile);
			previousState = new ProfileState(clientProfile, cacheProfile.getContactsCount());
			ServerProfile profile = cacheProfile.loadFullProfile();
			String message = messageBuilder.getProfileMessage(profile);
			writer.writeMessage(message);
			Collection<TalkID> talkIDs = profile.getTalksID();
			previousState.setTalksCount(talkIDs.size());
			Collection <ServerTalk> talks = ServerManager.getTalks(talkIDs);
			for (ServerTalk talk : talks){
				previousState.addTalkState(talk.getId(), talk.getParticipantsCount(), talk.getMessagesCount());
				message = messageBuilder.getTalkMessage(talk);
				writer.writeMessage(message);
			}
			try {
				writer.writeMessage(Protocol.SERVICE_HEADER+Protocol.Delimiter.Message+Protocol.Message.End);
				getClientConfirmation();
			} catch (IOException e) {
				e.printStackTrace();
			}
			addConnectionAndUpdate(previousState);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}	
		
	}


	private void addConnectionAndUpdate(ProfileState state) {
		ProfileLoadConnection connection = new ProfileLoadConnection(state.getName(), socket);
		ClientsManager.addConnetion(connection);
		updateProfileWithLocks(state, connection);
		CleanConnection clean = new CleanConnection(connection);
		ClientsManager.changeConnection(connection, clean);
		
	}


	private void updateProfileWithLocks(ProfileState state, ProfileLoadConnection connection) {
		Collection <TalkState> talkStates = state.getTalkStates();
		updateTalks(talkStates, connection);
		CacheProfile profile = server.getProfiles().get(state.getName());
		profile.lock();
		try {
			loadNewTalks(profile, state, connection);
			loadNewContacts(profile, state);
		} finally{
			profile.unlock();
		}	
	}


	private void loadNewContacts(CacheProfile profile, ProfileState state) {
		if (profile.getContactsCount()> state.getContactsCount()){
			Collection <TalkParticipant> newContacts = profile.getContacts(state.getContactsCount()+1);
			for (TalkParticipant contact : newContacts){
				String message = messageBuilder.getContactMessage(contact.getName());
				try {
					writer.writeMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private void loadNewTalks(CacheProfile profile, ProfileState state, ProfileLoadConnection connection) {
		if (profile.getTalksCount()>state.getTalksCount()){
			Collection<TalkID> talkIDs = profile.getNewTalkIDs(state.getTalksCount()+1);
			Collection <ServerTalk> talks = ServerManager.getTalks(talkIDs);
			Collection <TalkState> talkStates = new LinkedList<ProfileState.TalkState>();
			for (ServerTalk talk : talks){
				talkStates.add(state.createTalkState(talk.getId(), talk.getParticipantsCount(), talk.getMessagesCount()));
				String string = messageBuilder.getTalkMessage(talk);
				try {
					writer.writeMessage(string);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			updateTalks(talkStates, connection);
		}
	}


	private void updateTalks(Collection<TalkState> talkStates, ProfileLoadConnection connection) {
		for (ProfileState.TalkState talkState : talkStates){
			CacheTalk talk = server.getTalks().get(talkState.getId());
			talk.lock();
			try {
				if (talk.getMessagesCount()>talkState.getMessagesCount()){
					loadNewTalkMessages(talk, talkState.getMessagesCount()+1);
				} 
				if (talk.getParticipantsCount()> talkState.getParticipantsCount()){
					loadNewTalkParticipants(talk, talkState.getParticipantsCount()+1);
				}
				connection.addTalkID(talk.getId());
			}finally{
				talk.unlock();
			}
		}
		
	}


	private void loadNewTalkParticipants(CacheTalk talk, int from) {
		Collection <TalkParticipant> participants = talk.getParticipants(from);
		for (TalkParticipant participant : participants){
			String string = messageBuilder.getParticipantMessage(participant.getName(), talk.getId());
			try {
				writer.writeMessage(string);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	private void loadNewTalkMessages(CacheTalk talk, int position) {
		Collection <ServerMessage> messages = talk.getMessages(position);
		for(ServerMessage message : messages){
			String string = messageBuilder.getServerMessageMessage(message, talk.getId());
			try {
				writer.writeMessage(string);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}


}
