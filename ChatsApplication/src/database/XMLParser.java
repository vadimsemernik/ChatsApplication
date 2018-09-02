package database;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.logic.entities.ServerMessage;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;

public class XMLParser {
	
	private static Set <ServerProfile> databaseProfiles = new HashSet<ServerProfile>(); 
	private static Set <ServerTalk> databaseTalks =  new HashSet<ServerTalk>();
	
	private static int defaultContactsVersion = 1;
	
	
	
	
	

	public static void addDataToStorage(String xml, TemporaryStorage database) {
		System.out.println("Start");
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(xml);
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
			for (ServerProfile profile : databaseProfiles){
				database.addProfile(profile);
			}
			for (ServerTalk talk: databaseTalks){
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
	


	private static void getProfilesFromXML(Node node, Set <ServerProfile> destination) {
		NodeList profiles = node.getChildNodes();
		for (int i=0;i<profiles.getLength();i++){
			Node profileNode = profiles.item(i);
			if (profileNode.getNodeType()!=Node.TEXT_NODE){
				if (profileNode.getNodeName().equals(XMLTags.PROFILE)){
					destination.add(getProfileFromNode(profileNode));
					}
				}
			}
		}
	
	private static ServerProfile getProfileFromNode(Node node) {
		ServerProfile result = null;
		String name = null;
		String password = null;
		List <TalkParticipant>contacts=null;
		List<TalkID> talksID = null;
		NodeList fields = node.getChildNodes();
		for(int i=0;i<fields.getLength();i++){
			Node field = fields.item(i);
			if (field.getNodeType()!=Node.TEXT_NODE){
				if (field.getNodeName().equals(XMLTags.NAME)){
					name=getTextFromNode(field);
				} else if (field.getNodeName().equals(XMLTags.PASSWORD)){
					password = getTextFromNode(field);
				} else if (field.getNodeName().equals(XMLTags.CONTACTS)){
					contacts = getContactsFromNode(field, XMLTags.CONTACT);
				} else if (field.getNodeName().equals(XMLTags.TALKSID)){
					talksID =  getTalksIDFromNode(field, XMLTags.TALKSID);
				}
				
				
			}
		}
		result = new ServerProfile(name,password, contacts,talksID);
		return result;
	}


	private static List<TalkID> getTalksIDFromNode(Node node, String tag) {
		List <TalkID> result = new ArrayList<TalkID>();
		NodeList messages = node.getChildNodes();
		for (int i=0; i<messages.getLength(); i++){
			Node talkID = messages.item(i);
			if (talkID.getNodeType() != Node.TEXT_NODE){
				if (talkID.getNodeName().equals(XMLTags.TALK_ID)){
					TalkID id = getTalkIDFromNode(talkID);
					if (id!=null){
						result.add(id);
					}
				}
			}
		}
		return result;
	}

	private static TalkID getTalkIDFromNode(Node talkID) {
		int number=0, id = 0;
		NodeList attrib = talkID.getChildNodes();
		for (int i=0; i<attrib.getLength(); i++){
			Node current = attrib.item(i);
			if (current.getNodeType() != Node.TEXT_NODE){
				if (current.getNodeName().equals(XMLTags.NUMBER)){
					number=Integer.valueOf(getTextFromNode(current));
				} else if (current.getNodeName().equals(XMLTags.ID)){
					id = Integer.valueOf(getTextFromNode(current));
				}
			}
		}
		if (id >0 && number>0){
			return new TalkID(number,id);
		}
		return null;
	}



	private static List<ServerMessage> getMessagesFromNode(Node node) {
		List <ServerMessage> result = new ArrayList<ServerMessage>();
		NodeList messages = node.getChildNodes();
		for (int i=0; i<messages.getLength(); i++){
			Node message = messages.item(i);
			if (message.getNodeType() != Node.TEXT_NODE){
				if (message.getNodeName().equals(XMLTags.MESSAGE)){
					result.add(getMessageFromNode(message));
				}
			}
		}
		return result;
	}


	private static ServerMessage getMessageFromNode(Node node) {
		ServerMessage result=null;
		String author=null;
		String content=null;
		int number=0;
		NodeList attributes = node.getChildNodes();
		for (int i=0;i<attributes.getLength();i++){
			Node attrib = attributes.item(i);
			if (attrib.getNodeType()!=Node.TEXT_NODE){
				if (attrib.getNodeName().equals(XMLTags.AUTHOR)){
					author=getTextFromNode(attrib);
				} else if (attrib.getNodeName().equals(XMLTags.CONTENT)){
					content = getTextFromNode(attrib);
				} else if (attrib.getNodeName().equals(XMLTags.NUMBER)){
					number=Integer.valueOf(getTextFromNode(attrib));
				}
			}
		} if (author != null && content!=null && number>0){
			result = new ServerMessage(number, author, content);
		}
		return result;
	}



	private static List<TalkParticipant> getContactsFromNode(Node node, String tag) {
		List <TalkParticipant> result = new ArrayList <TalkParticipant>();
		NodeList contacts = node.getChildNodes();
		for (int i=0;i<contacts.getLength();i++){
			Node contactNode = contacts.item(i);
			if (contactNode.getNodeType()!=Node.TEXT_NODE){
				if (contactNode.getNodeName().equals(tag)){
					TalkParticipant contact = getContactFromNode(contactNode);
					if (contact != null){
						result.add(contact);
					}
				}
			}
		}
		return result;
	}


	private static TalkParticipant getContactFromNode(Node contactNode) {
		String contactName = null;
		int number = 0;
		NodeList attrib = contactNode.getChildNodes();
		for (int i=0; i<attrib.getLength(); i++){
			Node current = attrib.item(i);
			if (current.getNodeType() != Node.TEXT_NODE){
				if (current.getNodeName().equals(XMLTags.NUMBER)){
					number=Integer.valueOf(getTextFromNode(current));
				} else if (current.getNodeName().equals(XMLTags.NAME)){
					contactName = getTextFromNode(current);
				}
			}
		}
		if (contactName!=null && number>0){
			return new TalkParticipant(number,contactName);
		}
		return null;
	}



	private static String getTextFromNode(Node node) {
		String result = node.getChildNodes().item(0).getTextContent();
		result.trim();
		return result;
	}
	
	private static void getTalksFromXML(Node node, Set<ServerTalk> destination) {
		NodeList talks = node.getChildNodes();
		for (int i=0;i<talks.getLength();i++){
			Node talk = talks.item(i);
			if (talk.getNodeType()!=Node.TEXT_NODE){
				if (talk.getNodeName().equals(XMLTags.TALK)){
					destination.add(createNewtalk(talk));
				}
			}
		}
		
	}


	private static ServerTalk createNewtalk(Node talkNode) {
		ServerTalk result = null;
		String title = null;
		int id=0;
		List <TalkParticipant> participants = null;
		List <ServerMessage> messages = null;
		NodeList talkAttrib = talkNode.getChildNodes();
		for (int i=0;i<talkAttrib.getLength();i++){
			Node attrib = talkAttrib.item(i);
			if (attrib.getNodeType()!=Node.TEXT_NODE){
				String name = attrib.getNodeName();
				if (name.equals(XMLTags.TITLE)){
					title=getTextFromNode(attrib);
				} else if (name.equals(XMLTags.ID)){
					id = Integer.valueOf(getTextFromNode(attrib));
				} else if (name.equals(XMLTags.PARTICIPANTS)){
					participants = getContactsFromNode(attrib, XMLTags.PARTICIPANT);
				} else if (name.equals(XMLTags.MESSAGES)){
					messages = getMessagesFromNode(attrib);
				} 
			}
		}
		result = new ServerTalk(title, id, participants, messages);
		return result;
	}

}
