package server.login;

public class LoginChecker {
	
	public static String checkAccount(String name, String password){
		String account = Accounts.findAccount(name, password);
		return account;
	}

}
