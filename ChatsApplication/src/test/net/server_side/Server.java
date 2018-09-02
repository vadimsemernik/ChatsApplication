package test.net.server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket server;
	private Thread worker;
	private SocketReceiver receiver;

	public Server(int port) {
		try {
			server = new ServerSocket(port);
			receiver = new SocketReceiver();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		worker = new Thread (new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Socket socket = server.accept();
						System.out.println("New socket came");
						receiver.add(socket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		worker.start();
	}

}
