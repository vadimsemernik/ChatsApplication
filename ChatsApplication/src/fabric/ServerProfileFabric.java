package fabric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import server.logic.entities.ServerProfile;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;

public class ServerProfileFabric {
	
	private static int counter = 0;
	private static String defaultName = "ProfileName";
	private static String defaultPassword = "ProfilePassword";

	public static ServerProfile getServerProfile() {
		counter++;
		String name = defaultName+counter;
		String password = defaultPassword+counter;
		List <TalkParticipant> contacts = new ArrayList<TalkParticipant>();
		for (int i=0;i<3;i++){
			contacts.add(ServerContactFabric.getContact(i));
		}
		List <TalkID> talksID = new ArrayList<TalkID>();
		for (int i=0;i<3;i++){
			talksID.add(new TalkID(i, i));
		}
		ServerProfile profile = new ServerProfile(name, password, contacts, talksID);
		return profile;
	}
	
	public static ServerProfile getServerProfile(int contactsCount, int talksCount) {
		counter++;
		String name = defaultName+counter;
		String password = defaultPassword+counter;
		List <TalkParticipant> contacts = new ArrayList<TalkParticipant>();
		for (int i=0;i<contactsCount;i++){
			contacts.add(ServerContactFabric.getContact(i));
		}
		List <TalkID> talksID = new ArrayList<TalkID>();
		for (int i=0;i<talksCount;i++){
			talksID.add(new TalkID(i, i));
		}
		ServerProfile profile = new ServerProfile(name, password, contacts, talksID);
		return profile;
	}

}
