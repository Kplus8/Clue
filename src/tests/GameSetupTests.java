package tests;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 *
 */

public class GameSetupTests {

	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data\\Map.csv", "data\\ClueRooms.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testLoadingPeople() {

		
		
	}

	@Test
	public void testLoadDeck() {

	}

	@Test
	public void testDealCards() {

	}

	@Test
	public void testTargetLocation() {

	}

	@Test
	public void testAccusation() {

	}

	@Test
	public void testCreateSuggestion() {

	}

	@Test
	public void testDisproveSuggestion() {
		
	}
	
	@Test
	public void testPlayerDisproveSuggestion() {
		
	}
	
}
