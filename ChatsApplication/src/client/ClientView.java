package client;

import client.gui.*;
import client.gui.settings.ComponentSizes;
import client.logic.ClientLogic;
import client.logic.entities.Contact;

public class ClientView {
	
	
	private ClientWindow window;
	//the source of client information
	private ClientProfile profile;
	

	public ClientView(ClientProfile profile) {
		this.profile=profile;
		ComponentSizes.setSizes();
		window = WindowImp.getClientWindow(profile);
		GeneralSettings.setUser(new Contact(profile.getName()));
		
	}
	
	
	public void lostConnection(){
		window.lostConnection();
	}
	
	public void resumeConnection(){
		window.resumeConnection();
	}

}
