package client.net.client_side.connections;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import client.GeneralSettings;
import client.logic.utils.SharedQueue;
import client.net.client_side.net_protocol.ProtocolReader;


public class MessageReader implements Closeable{
	
	private ProtocolReader reader;
	private volatile boolean running;
	private Thread worker;
	private SharedQueue <String> messages;

	public MessageReader(InputStream input, SharedQueue<String> messages) {
		this.reader = new ProtocolReader (input);
		this.messages = messages;
		running = true;
		initWorker();
		worker.start();
		
	}

	private void initWorker() {
		worker = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String message;
				while (running){
					try {
						message = reader.readMessage();
						messages.add(message.toString());
					} catch (SocketException e){
						GeneralSettings.getView().lostConnection();
					}
					catch (IOException e) {
						e.printStackTrace();
					} 
					
				}
				
			}
	
		}, "worker");
		
	}

	@Override
	public void close() throws IOException {
		running = false;
		if (worker != null) worker.interrupt();
		if (reader!=null) reader.close();
		
	}
	
	

}
