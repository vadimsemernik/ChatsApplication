package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;

public class TemporaryStorage {
	
	private List <ServerProfile> profiles = new ArrayList <ServerProfile>();
	private Map <String, String> accounts = new HashMap<String,String>();
	private List <ServerTalk> talks = new ArrayList<ServerTalk>();
	
	

	


	public void addProfile(ServerProfile profile) {
		profiles.add(profile);
		accounts.put(profile.getName(), profile.getPassword());
		
	}

	public void addTalk(ServerTalk talk) {
		talks.add(talk);
		
	}

	public List<ServerProfile> getProfiles() {
		return profiles;
	}

	public List<ServerTalk> getTalks() {
		return talks;
	}
	
	public Map<String, String> getAccounts() {
		return accounts;
	}


	
	

}
