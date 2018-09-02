package server.logic.messageParsers;

import java.util.LinkedList;
import java.util.List;

import server.logic.stateUpdates.ServerStateUpdaterExecutor;
import server.logic.stateUpdates.UpdateTask;
import server.logic.stateUpdates.tasks.ContactTask;
import server.logic.stateUpdates.tasks.MessageTask;
import server.logic.stateUpdates.tasks.ParticipantTask;
import server.logic.stateUpdates.tasks.TalkTask;
import server.protocol.Protocol;


public class ParsingTask implements Runnable{
	
	private String message;

	
	public ParsingTask (String message){
		this.message = message;
	}
	
	
	
	@Override
	public void run() {
		parseMessage();
		
	}



	private void parseMessage() {
		String [] parts = message.split(Protocol.Delimiter.Outer.toString());
		String header = parts[0];
		if (header.startsWith(Protocol.SERVICE_HEADER)){
			parseServiceMessage(parts);
			return;
		} else if (header.startsWith(Protocol.INFO_HEADER)){
			parseLogicMessage(parts);
		} else {
			System.out.println("Something wrong with parsing task!!!!!!!!!!!!!!");
		}
		
		
	}



	private void parseLogicMessage(String[] messageParts) {
		// extract logic header
		System.out.println("ParsingTask : parseLogicMessage");
		String header  = (messageParts[0].split(Protocol.Delimiter.Message.toString()))[1];
		if (Protocol.LogicHeader.Contact.toString().equals(header)) {
			addContactToProfile(messageParts[1],messageParts[2]);
		} else if (Protocol.LogicHeader.Message.toString().equals(header)) {
			addMessageToTalk(messageParts[1],messageParts[2], messageParts[3]);
		} else if (Protocol.LogicHeader.Participant.toString().equals(header)) {
			addParticipantToTalk(messageParts[1],messageParts[2]);
		} else if (Protocol.LogicHeader.Talk.toString().equals(header)) {
			addNewTalk(messageParts);
		} else {
			System.out.println("Something wrong in ParseLogicMessage !!!!!!!!!!!!!!");
		}
	}



	private void parseServiceMessage(String[] parts) {
		System.out.println("ParsingTask : parseServiceMessage");
		
	}



	private void addNewTalk(String [] array) {
		String title = array[1];
		List <String> participants = new LinkedList<String>();
		for (int i = 2; i<array.length;i++){
			participants.add(array[i]);
		}
		UpdateTask task = new TalkTask(title, participants);
		ServerStateUpdaterExecutor.addTask(task);
		
	}



	private void addParticipantToTalk(String participant, String talkID) {
		UpdateTask task = new ParticipantTask(participant, Integer.valueOf(talkID));
		ServerStateUpdaterExecutor.addTask(task);
		
	}



	private void addMessageToTalk(String talkID, String author, String message) {
		UpdateTask task = new MessageTask(message, author, Integer.valueOf(talkID));
		ServerStateUpdaterExecutor.addTask(task);
		
	}



	private void addContactToProfile(String contact, String profile) {
		UpdateTask task = new ContactTask(contact, profile);
		ServerStateUpdaterExecutor.addTask(task);
		
	}

}
