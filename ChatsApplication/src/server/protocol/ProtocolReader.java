package server.protocol;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class ProtocolReader implements Closeable{
	
	private char messageEnd;
	private char symbolsEnd;
	private BufferedInputStream input;
	
	
	public ProtocolReader(InputStream stream){
		input = new BufferedInputStream(stream);
		messageEnd = Protocol.END;
		symbolsEnd = (char)-1;
	}
	
	public String readMessage() throws IOException{
		StringBuilder builder = new StringBuilder();
		char ch = (char) input.read();
		while (ch != messageEnd && ch != symbolsEnd){
			builder.append(ch);
			ch = (char) input.read();
		}
		return builder.toString();
	}

	@Override
	public void close() throws IOException {
		if (input != null){
			input.close();
		}
		
	}

}
