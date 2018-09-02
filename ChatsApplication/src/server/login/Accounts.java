package server.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.net.client_side.net_protocol.Protocol;
import server.logic.entities.cache_entities.CacheProfile;

public class Accounts {
	
	public static Map <String, CacheProfile> accounts;
	
	public static void setAccounts(Map <String, CacheProfile> profiles){
		accounts = profiles;
	}

	public static String findAccount(String name, String password) {
		if (accounts==null){
			throw new IllegalArgumentException("Accounts is null");
		}
		CacheProfile profile = accounts.get(name);
		if (profile!=null){
			if (profile.getPassword().equals(password)){
				return Protocol.Login.Found.toString()
						+Protocol.Delimiter.Message.toString()
						+name;
			} else {
				return Protocol.Login.NotFound.toString()
						+Protocol.Delimiter.Message.toString()
						+Protocol.Login.InvalidPassword.toString();
			}
		}
		return Protocol.Login.NotFound.toString()
				+Protocol.Delimiter.Message.toString()
				+Protocol.Login.InvalidLogin.toString();
	}
	
	public static void addAccount(CacheProfile profile){
		accounts.put(profile.getName(), profile);
	}
	
	

}
