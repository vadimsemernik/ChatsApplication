package test.xml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import server.logic.entities.ServerProfile;

public class TestParser {
	
	static String database = "test.xml";

	public static void main(String[] args) {
		List <String> contacts = newList("Deadpool", "Weasel", "Cable");
		List <Integer> talksID = newList(3);
		//ServerProfile russel = new ServerProfile("Russel", "InnerPocket11", contacts, talksID);
		//writeProfile(russel, database);

	}

	private static void writeProfile(ServerProfile russel, String file) {
		XMLParser parser = new XMLParser(file);
		parser.addItem("Profiles", "Profile", "Russel");
	}

	private static <E> List<E> newList(E ... items) {
		List <E> set = new ArrayList<E>();
		for (E item:items){
			set.add(item);
		}
		return set;
	}

}
