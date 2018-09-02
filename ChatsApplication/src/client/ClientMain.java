package client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import client.gui.ClientWindowExample;
import client.logic.ClientLogic;
import client.logic.entities.Contact;
import client.logic.entities.ClientTalk;

public class ClientMain {

	public static void main(String[] args) {
		Set<Contact> contacts = new CopyOnWriteArraySet<Contact>();
		Contact vadim = new Contact ("Vadim");
		Contact sasha = new Contact("Sasha");
		Contact pasha = new Contact("Pasha");
		Contact greg = new Contact("Greg");
		contacts.add(sasha);
		contacts.add(pasha);
		contacts.add(greg);
		Set <Contact> firstParty = new HashSet<Contact>();
		firstParty.add(vadim);
		firstParty.add(greg);
		ClientTalk first = new ClientTalk("Dialog between Vadim and Greg", 1, firstParty , null);
		Set <Contact> secondParty = new HashSet<Contact>();
		secondParty.add(vadim);
		secondParty.add(sasha);
		secondParty.add(pasha);
		ClientTalk second = new ClientTalk("Conversation between Vadim, Sasha and Pasha", 2, secondParty, null);
		
		Collection <ClientTalk> conversations = new ArrayList <ClientTalk> ();
		conversations.add(first);
		conversations.add(second);
		ClientProfile profile = new ClientProfile(vadim.getName(),"Gonein60years", contacts, conversations);
		ClientLogic processor = new ClientLogic(profile);
		
		ClientView view = new ClientView (profile);

	}

}
