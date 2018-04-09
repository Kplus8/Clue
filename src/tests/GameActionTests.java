package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;

/**
* Tests to see if file game actions work properly
*
* @author Jim DeBlock
* @author Graham Kitchenka
* @author Brandon Verkamp
*/

public class GameActionTests {
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data" + File.separatorChar + "Map.csv",
				"data" + File.separatorChar + "ClueRooms.txt");
		// Initialize will load BOTH config files
		board.initialize();

		board.loadConfigFiles();
	}
	
	@Test
	public void testDisproveSuggestion() {
		
	}
	
	@Test
	public void testCreateSuggestion() {
		
	}
	
	@Test
	public void testCheckAccusation() {
		
	}
	
	@Test
	public void testPlayerDisproveSuggestion() {
		
	}
	
	@Test
	public void testTargetLocation() {
		ComputerPlayer player = new ComputerPlayer();
		 // Pick a location with no rooms in target, just three targets
		 board.calcTargets(13, 21, 6);
		 boolean loc_13_15 = false;
		 boolean loc_14_16 = false;
		 boolean loc_12_16 = false;
		 // Run the test a large number of times
		 for (int i=0; i<100; i++) {
		 BoardCell selected = player.pickLocation(board.getTargets());
		 if (selected == board.getCellAt(13, 15))
		 loc_13_15 = true;
		 else if (selected == board.getCellAt(14, 16))
		 loc_14_16 = true;
		 else if (selected == board.getCellAt(12, 16))
		 loc_12_16 = true;
		 else
		 fail("Invalid target selected");
		 }
		 // Ensure each target was selected at least once
		 assertTrue(loc_13_15);
		 assertTrue(loc_14_16);
		 assertTrue(loc_12_16);

	}
	
}
