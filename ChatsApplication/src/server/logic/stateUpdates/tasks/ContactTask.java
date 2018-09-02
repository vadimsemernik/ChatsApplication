package server.logic.stateUpdates.tasks;

import server.logic.stateUpdates.UpdateTask;

public class ContactTask implements UpdateTask {
	
	private String contact;
	private String profile;

	public ContactTask(String contact, String profile) {
		this.contact = contact;
		this.profile = profile;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public String getContact() {
		return contact;
	}

	public String getProfile() {
		return profile;
	}
	
	

}
