package server.clients.connections;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketException;

import server.login.ProfileLoadConnection;
import server.protocol.ProtocolReader;
import server.utils.SharedQueue;

public class ServerMessageReader implements Closeable{
	
	private ProtocolReader reader;
	private volatile boolean running;
	private Thread worker;
	private SharedQueue<String> newMessages;
	private ClientConnection connection;

	public ServerMessageReader(InputStream input, ClientConnection connection) {
		this.reader = new ProtocolReader(input);
		this.connection = connection;
		running = true;
		initWorker();
		
	}

	private void initWorker() {
		worker = new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (newMessages == null){
					throw new IllegalArgumentException("There is no destination for client messages");
				}
				while (running){
					try {
						String message = reader.readMessage();
						receiveMessage(message);
					} catch (SocketException e){
							connection.close();
					}catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
	
		}, "worker");
		
	}
	
	private void receiveMessage(String message) {
		newMessages.add(message);
	}


	public void setReceiveQueue(SharedQueue<String> messages) {
		newMessages = messages;
		worker.start();
	}

	@Override
	public void close() throws IOException {
		if (worker !=null) worker.interrupt();
		running = false;
		if (reader!=null) reader.close();
		
	}

	

}
