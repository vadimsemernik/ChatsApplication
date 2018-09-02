package client.database;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import client.ClientProfile;
import client.GeneralSettings;
import client.logic.entities.Contact;
import client.logic.entities.Message;
import client.logic.entities.ClientTalk;
import client.database.XMLTags;

public class ClientDatabaseContext {
	
	private static ClientDatabase database = GeneralSettings.getDatabase();
	private static Set <ClientProfile> databaseProfiles = new HashSet<ClientProfile>(); 
	private static Set <ClientTalk> databaseTalks =  new HashSet<ClientTalk>();
	
	public static void addProfilesToDatabase(String databaseContext, ClientDatabase database) {
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(databaseContext);
			Node root = document.getDocumentElement();
			NodeList rootChildren = root.getChildNodes();
			for (int i=0; i<rootChildren.getLength();i++){
				Node rootChild = rootChildren.item(i);
				if (rootChild.getNodeType() != Node.TEXT_NODE){
					if (rootChild.getNodeName().equals(XMLTags.PROFILES)){
						getProfilesFromXML(rootChild, databaseProfiles);
					} if (rootChild.getNodeName().equals("Talks")){
						getTalksFromXML(rootChild, databaseTalks);
					}
				}
			} 
			addTalksToProfiles();
			for (ClientProfile profile : databaseProfiles){
				database.addProfile(profile);
			}
			for (ClientTalk talk: databaseTalks){
				database.addTalk(talk);
			}
		}  catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        }catch (IOException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	private static void addTalksToProfiles() {
		for (ClientTalk talk :databaseTalks){
			Set <Contact> clients = talk.getParticipants();
			for (Contact client : clients){
				for (ClientProfile profile : databaseProfiles){
					if (profile.getName().equals(client.getName())){
						profile.addTalk(talk);
					}
				}
			}
		}
		
	}


	public static void initContext(String databaseContext, String serverContext){
		addContextToDatabase(databaseContext);
	}
	
	
	
	private static void addContextToDatabase(String databaseContext) {
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(databaseContext);
			Node root = document.getDocumentElement();
			NodeList rootChildren = root.getChildNodes();
			for (int i=0; i<rootChildren.getLength();i++){
				Node rootChild = rootChildren.item(i);
				if (rootChild.getNodeType() != Node.TEXT_NODE){
					if (rootChild.getNodeName().equals("Profiles")){
						getProfilesFromXML(rootChild, databaseProfiles);
					} if (rootChild.getNodeName().equals("Talks")){
						getTalksFromXML(rootChild, databaseTalks);
					}
				}
			}
			addTalkstoProfile(databaseTalks,databaseProfiles);
			for (ClientProfile profile : databaseProfiles){
				database.addProfile(profile);
			}
		}  catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        }catch (IOException e){
			System.out.println(e.getMessage());
		}
		
	}

	
	private static void addTalkstoProfile(Set<ClientTalk> talks, Set<ClientProfile> profiles) {
		Set <Contact> participants;
		Map <String, ClientProfile> mediator = new HashMap <String, ClientProfile>();
		for (ClientProfile profile : profiles){
			mediator.put(profile.getName(), profile);
		}
		ClientProfile current;
		for (ClientTalk talk : talks){
			participants = talk.getParticipants();
			for (Contact participant : participants){
				current = 	mediator.get(participant.getName());
				if (current!=null){
					current.addTalk(talk);
				}
			}
		}
		
	}


	private static void getProfilesFromXML(Node node, Set <ClientProfile> destination) {
		NodeList profiles = node.getChildNodes();
		for (int i=0;i<profiles.getLength();i++){
			Node profileNode = profiles.item(i);
			if (profileNode.getNodeType()!=Node.TEXT_NODE){
				if (profileNode.getNodeName().equals("Profile")){
					destination.add(getProfileFromNode(profileNode));
					}
				}
			}
		}
	
	private static ClientProfile getProfileFromNode(Node node) {
		ClientProfile result = null;
		String name = null;
		String password = null;
		Set<Contact>contacts=null;
		Collection <ClientTalk> talks = new ArrayList<ClientTalk>();
		NodeList fields = node.getChildNodes();
		for(int i=0;i<fields.getLength();i++){
			Node field = fields.item(i);
			if (field.getNodeType()!=Node.TEXT_NODE){
				if (field.getNodeName().equals("Name")){
					name=getTextFromNode(field);
				} else if (field.getNodeName().equals("Password")){
					password = getTextFromNode(field);
				} else if (field.getNodeName().equals("Contacts")){
					contacts = getContactsFromNode(field, "Contact");
				}
				
				
			}
		}
		result = new ClientProfile(name,password, contacts,talks);
		return result;
	}


	private static Queue<Message> getMessagesFromNode(Node node) {
		Queue <Message> result = new ArrayDeque<Message>();
		NodeList messages = node.getChildNodes();
		for (int i=0; i<messages.getLength(); i++){
			Node message = messages.item(i);
			if (message.getNodeType() != Node.TEXT_NODE){
				if (message.getNodeName().equals("Message")){
					result.add(getMessageFromNode(message));
				}
			}
		}
		return result;
	}


	private static Message getMessageFromNode(Node node) {
		Message result=null;
		String author=null;
		String content=null;
		NodeList attributes = node.getChildNodes();
		for (int i=0;i<attributes.getLength();i++){
			Node attrib = attributes.item(i);
			if (attrib.getNodeType()!=Node.TEXT_NODE){
				if (attrib.getNodeName().equals("Author")){
					author=getTextFromNode(attrib);
				} else if (attrib.getNodeName().equals("Content")){
					content = getTextFromNode(attrib);
				}
			}
		}
		result = new Message(new Contact(author), content);
		return result;
	}



	private static Set<Contact> getContactsFromNode(Node node, String tag) {
		Set <Contact> result = new HashSet <Contact>();
		NodeList contacts = node.getChildNodes();
		for (int i=0;i<contacts.getLength();i++){
			Node contactNode = contacts.item(i);
			if (contactNode.getNodeType()!=Node.TEXT_NODE){
				if (contactNode.getNodeName().equals(tag)){
					String author = getTextFromNode(contactNode);
					Contact contact = new Contact (author);
					result.add(contact);
				}
			}
		}
		return result;
	}


	private static String getTextFromNode(Node node) {
		String result = node.getChildNodes().item(0).getTextContent();
		result.trim();
		return result;
	}
	
	private static void getTalksFromXML(Node node, Set<ClientTalk> destination) {
		NodeList talks = node.getChildNodes();
		for (int i=0;i<talks.getLength();i++){
			Node talk = talks.item(i);
			if (talk.getNodeType()!=Node.TEXT_NODE){
				if (talk.getNodeName().equals("Talk")){
					destination.add(createNewtalk(talk));
				}
			}
		}
		
	}


	private static ClientTalk createNewtalk(Node talkNode) {
		ClientTalk result = null;
		String title = null;
		int id=0;
		Set <Contact> participants = null;
		Queue <Message> messages = null;
		NodeList talkAttrib = talkNode.getChildNodes();
		for (int i=0;i<talkAttrib.getLength();i++){
			Node attrib = talkAttrib.item(i);
			if (attrib.getNodeType()!=Node.TEXT_NODE){
				String name = attrib.getNodeName();
				if (name.equals("Title")){
					title=getTextFromNode(attrib);
				} else if (name.equals("ID")){
					id = Integer.valueOf(getTextFromNode(attrib));
				} else if (name.equals("Participants")){
					participants = getContactsFromNode(attrib, "Participant");
				} else if (name.equals("Messages")){
					messages = getMessagesFromNode(attrib);
				}
			}
		}
		result = new ClientTalk(title, id, participants, messages);
		return result;
	}
	
	
	
	public static void addProfileToDatabase(ClientProfile profile){
		database.addProfile(profile);
	}
	

	public static ClientDatabase getDatabase() {
		return database;
	}

	
	

}
