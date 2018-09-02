package server.logic.stateUpdates.tasks;

import server.logic.stateUpdates.UpdateTask;

public class ParticipantTask implements UpdateTask{
	
	private String participant;
	private int id;

	public ParticipantTask(String participant, Integer id) {
		this.participant = participant;
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public String getParticipant() {
		return participant;
	}

	public int getId() {
		return id;
	}
	
	

}
