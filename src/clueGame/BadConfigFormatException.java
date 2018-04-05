package clueGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * An exception that occurs as a result of a malformed or nonexistent (presumably) config
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class BadConfigFormatException extends Exception {
	/**
	 * A true unchecked exception - what are we supposed to do about a bad file? The user should
	 * check for this and handle higher up
	 */
	public BadConfigFormatException() {
		super("Unable to load config file - bad format.");
		try {
			log("Unable to load config file - bad format.");
		} catch (IOException e) {
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
