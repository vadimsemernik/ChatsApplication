package server.logic.stateUpdates.tasks;

import server.logic.entities.ServerMessage;
import server.logic.entities.cache_entities.CacheTalk;
import server.logic.stateUpdates.UpdateTask;
import server.server_state.ServerStateFactory;

public class MessageTask implements UpdateTask {
	
	private String content;
	private String author;
	private int talkID;

	public MessageTask(String content, String author, Integer talkID) {
		this.content = content;
		this.author = author;
		this.talkID = talkID;
	}

	@Override
	public void run() {
		CacheTalk talk = ServerStateFactory.getServer().getTalks().get(talkID);
		if (talk == null){
			throw new IllegalStateException("Trying to add message to non-exist talk");
		}
		talk.addMessage(author, content);
	}

	public String getMessage() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

	public int getTalkID() {
		return talkID;
	}
	
	

}
