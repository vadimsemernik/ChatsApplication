package server.protocol;


import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ProtocolWriter implements Closeable{
	
	private char messageEnd;
	private BufferedWriter output;

	public ProtocolWriter(OutputStream stream) {
		output = new BufferedWriter(new OutputStreamWriter(stream));
		messageEnd = Protocol.END;
	}
	
	public void writeMessage(String message) throws IOException{
		output.write(message);
		output.write(messageEnd);
		output.flush();
	}

	@Override
	public void close() throws IOException {
		if (output !=null){
			output.close();
		}
		
	}

}
