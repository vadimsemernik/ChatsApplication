package client.logic.handlers;

import client.ClientProfile;
import client.GeneralSettings;
import client.database.ClientDatabase;
import client.login.LoginWindow;
import client.net.Server;
import client.net.client_side.net_protocol.Protocol;

public class LoginHandler {
	
	private LoginWindow loginWindow;
	private ClientDatabase database = ClientDatabase.getDatabase();
	private Server server = Server.getServer();
	
	
	public LoginHandler (LoginWindow loginWindow){
		this.loginWindow = loginWindow;
		
	}
	
	public LoginHandler (Server server){
		this.server = server;
		
	}
	

	public void handle(String login, String password) {
		String result;
		ClientProfile profile = null;
		//profile = getFromDatabase(login, password);
		if (profile == null){
			result = server.search(login, password);
		} else {
			loginWindow.dispose();
			server.updateProfile(profile);
			GeneralSettings.setProfile(profile);
			return;
		}
		processServerAnswer(result);
	}
	
	

	private void processServerAnswer(String serverAnswer) {
		if (serverAnswer == null || serverAnswer.equals("")){
			loginWindow.accountIsNotFound();
		} else if (serverAnswer.startsWith(Protocol.Login.Found.toString())){
			loginWindow.dispose();
		} else if (serverAnswer.startsWith(Protocol.Login.NotFound.toString()) 
				&& serverAnswer.endsWith(Protocol.Login.InvalidPassword.toString())){
			loginWindow.invalidPassword();
		} else {
			loginWindow.accountIsNotFound();
		}
		
	}

	private ClientProfile getFromDatabase(String login, String password){
		ClientProfile profile = database.search(login, password);
		return profile;
	}

	public boolean loginIsValid(String login) {
		char [] chars=login.toCharArray();
		for (char ch : chars){
			if (!(Character.isLetter(ch)||Character.isDigit(ch))){
				return false;
			}
		}
		return true;
	}

	/*
	 * password must be at least 6 symbols, contains both letters and digits
	 */
	public boolean passwordIsValid(char[] passChars) {
		if (passChars.length<6) return false;
		boolean number = false;
		boolean letter = false;
		for (char ch : passChars){
			if (Character.isLetter(ch)){
				letter = true;
			} else if (Character.isDigit(ch)){
				number = true;
			} else {
				return false;
			}
		}
		return (number&&letter);
	}


}
