package fabric;

import server.logic.entities.TalkParticipant;

public class ServerContactFabric {
	
	private static String defaultContact = "Contact";
	private static int number=1;

	public static TalkParticipant getContact(int i) {
		// TODO Auto-generated method stub
		return new TalkParticipant(number++, defaultContact+1);
	}

}
