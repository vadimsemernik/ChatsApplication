package client.net.client_side.messages_parsing_logic;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

public class UnknownMessageException extends Exception {
	
	private static final String WARNING = "Unknown message: ";

	public UnknownMessageException(String message) {
		System.err.println(WARNING + message);
	}
	
	public UnknownMessageException(String message, PrintStream stream) {
		stream.println(WARNING + message);
	}
	
	public UnknownMessageException(String message, Writer writer) {
		try {
			writer.write(WARNING + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
