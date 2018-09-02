package server.logic.stateUpdates.tasks;

import java.util.List;

import server.logic.stateUpdates.UpdateTask;

public class TalkTask implements UpdateTask {
	
	private String title;
	private List <String> participants;

	public TalkTask(String title, List<String> participants) {
		this.title = title;
		this.participants = participants;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public String getTitle() {
		return title;
	}

	public List<String> getParticipants() {
		return participants;
	}
	
	

}
