package clueGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Unable to load config file - bad format.");
		try {
			log("Unable to load config file - bad format.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BadConfigFormatException(String message) {
		super(message);
		try {
			log(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void log(String message) throws IOException {

		File f = new File("log.txt");
		FileWriter fw = new FileWriter(f);
		fw.append("\n"+message);
		fw.close();
		
	}

}
