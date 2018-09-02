package server.data;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import server.logic.entities.ServerMessage;
import server.logic.entities.TalkID;
import server.logic.entities.TalkParticipant;
import server.logic.entities.cache_entities.CacheProfile;
import server.logic.entities.cache_entities.CacheTalk;

public class Launcher {

	public static void main(String[] args) {
		DBWorker worker = new DBWorker("jdbc:mysql://localhost:3306/storage", "root", "application");
		try {
			Collection <ServerMessage> title = worker.getTalkMessagesInBounds(1, 2, 3);
			for (ServerMessage id : title){
				System.out.println(id.getContent());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("finish");

	}

}
