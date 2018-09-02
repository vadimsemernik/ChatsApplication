package server.clients.connections;


import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import server.protocol.ProtocolWriter;


public class ServerMessageWriter implements Closeable{
	
	private ProtocolWriter writer;
	private ClientConnection connection;

	public ServerMessageWriter(OutputStream stream, ClientConnection connection) {
		writer = new ProtocolWriter(stream);
		this.connection = connection;
	}

	public boolean sendMessage(String message) {
		boolean result=false;
		try {
			writer.writeMessage(message);
			result = true;
		} catch (SocketException e){
			connection.close();
		}catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public void close() throws IOException {
		if (writer !=null){
			writer.close();
		}
		
	}
	
	

}
