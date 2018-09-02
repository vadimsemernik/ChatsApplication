package test.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientLog {
	
	private static File file = new File("client log.txt");
	
	

	
	public static void writeToLog(String string) {
		try (FileWriter writer = new FileWriter(file, true)){
			writer.write(string+'\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
