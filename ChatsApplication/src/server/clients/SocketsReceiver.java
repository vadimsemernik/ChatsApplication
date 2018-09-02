package server.clients;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.login.NewConnection;
import server.server_state.ServerState;

public class SocketsReceiver {
	
	private ServerSocket receiver;
	private int port;
	private boolean running;
	private Thread receive;
	
	public SocketsReceiver (int port){
		this.port = port;
		try {
			receiver = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		running=true;
		receive();
		
	}

	private void receive() {
		
		receive = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Socket socket=null;
				while (running){
					try {
						socket = receiver.accept();
					} catch (IOException e) {
						e.printStackTrace();
					}
					process(socket);
					
				}
				
			}

		}, "receive");
		receive.start();
		
	}
	
	private void process(Socket socket) {
		if (socket != null){
			try {
				new NewConnection(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public ServerSocket getReceiver() {
		return receiver;
	}

	public int getPort() {
		return port;
	}

	public boolean isRunning() {
		return running;
	}


	public Thread getReceive() {
		return receive;
	}


}
