package server.login;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import server.clients.ClientsManager;
import server.clients.connections.ClientConnection;
import server.protocol.Protocol;
import server.utils.SharedQueue;

public class ProfileLoadConnection extends ClientConnection{
	
	
	private Lock lock = new ReentrantLock();
	private List <Integer> talkIDs = new LinkedList<Integer>();
	
	
	public ProfileLoadConnection (String name, Socket socket) {
		super(name, socket);
	}
	
	
	
	public void bindReceiveQueue(SharedQueue<String> messages) {
		reader.setReceiveQueue(messages);
		
	}

	@Override
	public void close() {
		try {
			if (writer != null) writer.close();
			if (reader !=null) reader.close();
			ClientsManager.deleteConnection(this);
		} catch (IOException e){
			System.out.println("Connection close error");
		} finally {
			ClientsManager.deleteConnection(this);
		}
		
		
	}


	@Override
	public boolean sendMessage(String message) {
		lock.lock();
		try{
			int talkID = getIDFromMessage(message);
			if (talkIDs.contains(talkID)){
				return writer.sendMessage(message);
			}
		} finally{
			lock.unlock();
		}
		
		return false;
	}

	

	void addTalkID(int talkID){
		lock.lock();
		try{
			talkIDs.add(talkID);
		} finally{
			lock.unlock();
		}
		
	}
	
	private int getIDFromMessage(String message){
		String id = message.split(Protocol.Delimiter.Outer.toString())[1];
		return Integer.valueOf(id);
	}

	
	public void lock(){
		lock.lock();
	}
	
	public void unlock(){
		lock.unlock();
	}
	
	
	
}
