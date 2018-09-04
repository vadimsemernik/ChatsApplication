package client.logic.entities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import client.gui.chatsContainerPanelComponents.TalkView;

public class ClientTalk {
	
	private String title;
	private int id;
	private int messagesCount=0;
	private Set<Contact> participants;
	private Queue <Message> messages;
	private TalkView view;
	
	private int hash=0;
	
	public ClientTalk (String title, int id, Set <Contact> participants, Queue <Message> messages){
		this.title=title;
		this.id=id;
		if (participants==null){
			participants = new HashSet <Contact> ();
		}
		this.participants=participants;
		if (messages == null){
			messages = new LinkedList <Message>();
		}
		this.messages=messages;
		if (messages != null){
			messagesCount = messages.size();
		}
	}
	
	public void addParticipant (Contact participant){
		participants.add(participant);
	}
	
	public void addMessage(Message message){
		messages.add(message);
		messagesCount++;
		if (view != null){
			view.addMessage(message);
			view.repaintView();
		}
		
	}
	
	public void bindView(TalkView view){
		this.view=view;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof ClientTalk){
			ClientTalk conversation = (ClientTalk) obj;
			return title.equals(conversation.title)
					&&id==conversation.getId()
					&&messagesCount==conversation.getMessagesCount();
		}
		return false;
	}
	
	
	@Override
	public int hashCode(){
		if (hash == 0){
			int result = 21;
			result = 31*result + title.hashCode();
			result = 31*result + id;
			result = 31*result + messagesCount;
			hash=result;
		}
		return hash;
	}

	public String getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}

	public Set<Contact> getParticipants() {
		return participants;
	}

	public Queue<Message> getMessages() {
		return messages;
	}
	
	public int getMessagesCount() {
		return messagesCount;
	}

	public void update(String title, Set<Contact> participants, Queue<Message> messages) {
		this.title=title;
		for (Contact participant: participants){
			addParticipant(participant);
		}
		for (Message message : messages){
			addMessage(message);
		}
	}

	
	

}
