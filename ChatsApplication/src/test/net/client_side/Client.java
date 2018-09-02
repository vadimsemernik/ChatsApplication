package test.net.client_side;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Set;

import client.ClientProfile;
import client.ClientView;
import client.logic.entities.Contact;
import client.logic.entities.ClientTalk;
import client.net.client_side.net_protocol.Protocol;
import client.net.client_side.profile_initialization.ClientProfileBuilder;
import server.utils.SharedQueue;

public class Client {
	
	private Socket socket;
	private ClientProfileBuilder  clientBuilder;
	private SharedQueue<String> messages;
	private Worker clientWorker;

	public Client(int port) {
		try {
			socket = new Socket(InetAddress.getLocalHost(), port);
			//System.out.println("Connection established");
			messages = new SharedQueue<String> ();
			MessageReceiver receiver = new MessageReceiver();
			clientBuilder = new ClientProfileBuilder();
			clientWorker = new Worker();
			System.out.println("Client worker is starting...");
			clientWorker.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private class MessageReceiver {
		
		private BufferedReader reader;
		private Thread worker;
		private BufferedInputStream input;
		
		private MessageReceiver() throws IOException{
			reader = getReader(socket);
			worker = new Thread(new Runnable() {
				
				@Override
				public void run() {
					int count=0;
					StringBuilder builder = new StringBuilder();
					try (BufferedInputStream input = new BufferedInputStream(socket.getInputStream())){
						while (count<4){
							char ch;
							while ((ch=(char)input.read())!=-1){
								builder.append(ch);
								if (ch==Protocol.END){
									messages.add(builder.toString());
									count++;
									//System.out.println("Messages count "+count);
									//System.out.println(builder.toString());
									builder.delete(0, builder.length());
									if (count==5){
										break;
									}
								}
							}
						}
						//System.out.println("All messages have come!");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
			//System.out.println("MessageReceiver is initialized");
			worker.start();
			
			
		}
		
		private BufferedReader getReader(Socket socket) throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return reader;
		}
	}
	
	private class Worker extends Thread {
		
		private volatile boolean running;
		
		private String finish = Protocol.SERVICE_HEADER
				+ Protocol.Delimiter.Message
				+ Protocol.Message.End;
		
		private Worker(){
			running = true;
		}
		
		public void run () {
			while (running){
				String message = messages.getNext();
				//System.out.println("Client Worker get a message");
				parseMessage(message);
			}
			finishBuilding();
		}


		private void parseMessage(String message){
			//System.out.println(message);
			if (message.startsWith(finish)){
				//System.out.println("finish building");
				running = false;
				return;
			}
			clientBuilder.buildProfileFromMessage(message);
			
		}
	}

	public void finishBuilding() {
		ClientProfile profile = clientBuilder.getProfile();
		printProfile(profile);
		System.out.println("finish");
		ClientView view = new ClientView(profile);
		
	}

	private void printProfile(ClientProfile profile) {
		System.out.println("Name:"+profile.getName());
		System.out.println("Password:"+profile.getPassword());
		System.out.println("Contacts:");
		Set<Contact> contacts = profile.getContacts();
		for (Contact contact:contacts){
			System.out.println(contact.getName());
		}
		Collection<ClientTalk>talks = profile.getTalks();
		for (ClientTalk talk: talks){
			System.out.println(talk.getTitle());
		}
	}

}
