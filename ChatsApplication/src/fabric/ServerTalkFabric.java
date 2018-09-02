package fabric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import server.logic.entities.*;



public class ServerTalkFabric {
	
	private static String defaultTitle = "TalkTitle";
	private static int counter = 0;

	public static ServerTalk getServerTalk(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Set<ServerTalk> getServerTalks(ServerProfile profile) {
		Set<ServerTalk> talks = new HashSet<ServerTalk>();
		Collection<TalkID> ids = profile.getTalksID();
		Collection<TalkParticipant> contacts = profile.getContacts();
		for (TalkID id : ids){
			talks.add(createTalk(id, contacts));
		}
		return talks;
	}

	private static ServerTalk createTalk(TalkID id, Collection<TalkParticipant> contacts) {
		counter++;
		String title = defaultTitle+counter;
		Collection<TalkParticipant> participants = getParticipants(contacts);
		List<ServerMessage> messages = getMessages(participants);
		ServerTalk talk = new ServerTalk(title, id.getId(), participants, messages);
		return talk;
	}


	private static Collection<TalkParticipant> getParticipants(Collection<TalkParticipant> contacts) {
		Random random = new Random();
		Collection <TalkParticipant> participants = new ArrayList<TalkParticipant>();
		for (TalkParticipant contact : contacts){
			int dice = random.nextInt(2);
			if (dice == 0){
				participants.add(contact);
			}
		}
		if (participants.size()<2){
			return contacts;
		}
		return participants;
	}
	
	private static List<ServerMessage> getMessages(Collection<TalkParticipant> participants) {
		Random random = new Random();
		List <ServerMessage> messages = new LinkedList<ServerMessage>();
		Object [] authors =  participants.toArray();
		int bound = authors.length;
		int count = random.nextInt(5);
		count +=3;
		for (int i = 0; i<count;i++){
			int index = random.nextInt(bound);
			messages.add(ServerMessageFabric.getMessage((String)authors[index]));
		}
		
		return messages;
	}

}
