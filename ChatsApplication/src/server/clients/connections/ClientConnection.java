package server.clients.connections;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

import server.clients.ClientsManager;
import server.utils.SharedQueue;

public abstract class ClientConnection implements Closeable{

	protected String name;
	protected int id;
	protected Socket socket;
	protected ServerMessageReader reader;
	protected ServerMessageWriter writer;
	
	public ClientConnection (String name, Socket socket) {
		this.name = name;
		this.socket = socket;
		try {
			initReader();
		} catch (IOException e) {
			System.out.println("Something wrong with socket reader");
		}
		try {
			initWriter();
		} catch (IOException e) {
			System.out.println("Something wrong with socket writer");
		}
		
		
	}
	
	public boolean sendMessage(String message) {
		return writer.sendMessage(message);
		
	}
	
	private void initWriter() throws IOException {
		writer = new ServerMessageWriter(socket.getOutputStream(), this);
		
	}

	private void initReader() throws IOException {
		reader = new ServerMessageReader(socket.getInputStream(), this);
		
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
	

	
	public void setConnectionId(int connectionID) {
		this.id = connectionID;
	}

	public int getConnectionId() {
		return id;
	}
	
	public String getClientName() {
		return name;
	}


	public void setReader(ServerMessageReader reader) {
		this.reader = reader;
	}

	public void setWriter(ServerMessageWriter writer) {
		this.writer = writer;
	}
	
	
}
