package database;

import java.sql.SQLException;
import java.util.List;

import server.logic.entities.ServerMessage;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;

public class Launcher {

	public static void main(String[] args) {
		/*String xml = "testDatabase.xml";
		TemporaryStorage storage = new TemporaryStorage();
		XMLParser.addDataToStorage(xml, storage);
		DatabaseCreator creator = new DatabaseCreator(storage);
		creator.fillDatabase();*/
		DBWorker worker = new DBWorker("jdbc:mysql://localhost:3306/storage", "root", "application");
		try {
			List <ServerMessage> title = worker.getTalkMessages(3, 4);
			for (ServerMessage id : title){
				System.out.println(id.getNumber());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("finish");

	}

}
