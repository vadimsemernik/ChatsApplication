package client.net.client_side.connections;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import client.logic.utils.SharedQueue;
import client.net.client_side.net_protocol.ProtocolWriter;

public class MessageWriter implements Closeable{
	
	private ProtocolWriter writer;
	private volatile boolean  running;
	private Thread worker;
	private SharedQueue<String>messages;

	public MessageWriter(OutputStream stream, SharedQueue<String>messages) {
		writer = new ProtocolWriter(stream);
		this.messages = messages;
		initWorker();
		running = true;
		worker.start();
		
	}

	private void initWorker() {
		worker = new Thread(new Runnable(){

			@Override
			public void run() {
				while (running){
					String message="";
					message = messages.getNext();
					sendMessage(message);
				}
			}
			
		});
		
	}

	public boolean sendMessage(String message) {
		boolean result;
		try {
			writer.writeMessage(message);
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	@Override
	public void close() throws IOException{
		if (worker!=null) worker.interrupt();
		if (writer != null) writer.close();
			
		
	}

}
