package test.net.server_side;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Set;

import fabric.ServerProfileFabric;
import fabric.ServerTalkFabric;
import server.logic.entities.ServerProfile;
import server.logic.entities.ServerTalk;
import server.protocol.MessageBuilderFactory;
import server.protocol.ServerMessageBuilder;
import server.utils.SharedQueue;

public class SocketReceiver {
	
	private SharedQueue<Socket> sockets;
	private Thread worker;
	
	SocketReceiver(){
		sockets = new SharedQueue<Socket>();
		worker = new Thread (new Runnable() {
			
			@Override
			public void run() {
				while(true){
					Socket socket = sockets.getNext();
					//System.out.println("Next socket is processing");
					try {
						sendProfile(socket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		worker.start();
	}
	
	private void sendProfile(Socket socket) throws IOException {
		Writer writer = getWriter(socket);
		Reader reader = getReader(socket);
		Sender sender = new Sender(writer);
		MessageBuilder builder = new MessageBuilder(sender.getMessagesQueue());
		builder.sendData();
		
	}


	private Reader getReader(Socket socket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return reader;
	}

	private Writer getWriter(Socket socket) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		return writer;
	}

	public void add(Socket socket) {
		sockets.add(socket);
		
	}
	
	private class MessageBuilder {
		
		private SharedQueue<String>messages;
		private ServerProfile profile;
		private Set <ServerTalk> talks;
		private ServerMessageBuilder builder;
		
		
		private MessageBuilder(SharedQueue<String> queue){
			messages = queue;
			profile = ServerProfileFabric.getServerProfile(5,3);
			talks = ServerTalkFabric.getServerTalks(profile);
			builder = MessageBuilderFactory.getMessageBuilder();
			//System.out.println("MessageBuilder is initialized");
		}

		public void sendData() {
			String profileMessage = builder.getProfileMessage(profile);
			messages.add(profileMessage);
			//System.out.println("MessageBuilder has added profile message");
			for (ServerTalk talk:talks){
				String talkMessage = builder.getTalkMessage(talk);
				messages.add(talkMessage);
				//System.out.println("MessageBuilder has added talk message");
			}
			String finishMessage = builder.getFinishMessage();
			messages.add(finishMessage);
			
		}
		
		
	}
	
	private class Sender {
		
		private Writer writer;
		private SharedQueue<String>messages;
		private Thread worker;
		
		private Sender(Writer writer){
			this.writer=writer;
			messages = new SharedQueue<String>(30);
			worker = new Thread (new Runnable() {
				
				@Override
				public void run() {
					while(true){
						String message = messages.getNext();
						//System.out.println("Sender is sending next message");
						try {
							writer.write(message);
							writer.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			//System.out.println("Sender is initialized");
			worker.start();	
		}
		
		private SharedQueue<String> getMessagesQueue (){
			return messages;
		}
	}

}
