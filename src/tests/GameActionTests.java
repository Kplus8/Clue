package tests;

import static org.junit.Assert.*;

import java.io.File;

import clueGame.Card;
import clueGame.CardType;

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
		board.setConfigFiles("data" + File.separatorChar + "Map.csv", "data" + File.separatorChar + "ClueRooms.txt");
		// Initialize will load BOTH config files
		board.initialize();

		board.loadConfigFiles();
	}

	@Test
	public void testDisproveSuggestion() {

		// if player has matching card

		ComputerPlayer p = new ComputerPlayer();
		Card c = new Card("Knife", CardType.WEAPON);
		p.giveCard(c);

		board.makeSuggestion(c, new Card("Irrelevant", CardType.ROOM), new Card("irrelevant", CardType.PERSON));

		assertEquals(p.disproveSuggestion(), c);

		// if player has no matching card

		ComputerPlayer p2 = new ComputerPlayer();

		board.makeSuggestion(new Card("iRrelevant", CardType.WEAPON), new Card("Irrelevant", CardType.ROOM),
				new Card("irrelevant", CardType.PERSON));

		assertEquals(p2.disproveSuggestion(), null);

		// if player has more than one matching card

		ComputerPlayer p3 = new ComputerPlayer();

		Card c2 = new Card("Den", CardType.ROOM);
		p3.giveCard(c);
		p3.giveCard(c2);

		board.makeSuggestion(c, new Card("Den", CardType.ROOM), new Card("irrelevant", CardType.PERSON));

		boolean den = false;
		boolean knife = false;
		// Run the test a large number of times
		for (int i = 0; i < 100; i++) {
			Card selected = p3.disproveSuggestion();
			if (selected.equals(c))
				knife = true;
			else if (selected.equals(c2))
				den = true;
			else
				fail("Invalid target selected");
		}

		assertTrue(knife);
		assertTrue(den);
	}

	@Test
	public void testCreateSuggestion() {

		ComputerPlayer p = new ComputerPlayer();
		p.giveCard(new Card("Colonel Mustard", CardType.PERSON));
		p.giveCard(new Card("Knife", CardType.WEAPON));
		p.giveCard(new Card("Den", CardType.ROOM));
		
		p.createSuggestion();
		
	}

	@Test
	public void testHandleAccusation() {

	}
	
	@Test
	public void testMakeAccusation() {

	}


	@Test
	public void testTargetLocation() {
		ComputerPlayer player = new ComputerPlayer();
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(13, 21, 6);
		boolean loc_13_15 = false;
		boolean loc_14_16 = false;
		boolean loc_12_16 = false;
		for (int i = 0; i < 100; i++) {
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

		// test that if a room is in range it chooses it
		board.calcTargets(7, 8, 1);
		assertEquals(player.pickLocation(board.getTargets()), board.getCellAt(6, 8));

		// test that if room was just visited, it is not chosen
		board.calcTargets(7, 8, 1);
		assertNotEquals(player.pickLocation(board.getTargets()), board.getCellAt(6, 8));
	}

}
