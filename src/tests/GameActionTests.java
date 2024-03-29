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
import clueGame.Player;

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
	
	/**
	 * Test that disproving suggestions works as expected - returning the disproving card when
	 * disproval can be made or null when it cannot
	 */
	@Test
	public void testDisproveSuggestion() {

		// if player has matching card
		ComputerPlayer p = new ComputerPlayer();
		Card c = new Card("Knife", CardType.WEAPON);
		p.giveCard(c);

		board.makeSuggestion(c, new Card("Irrelevant", CardType.ROOM), new Card("irrelevant", CardType.PERSON));

		assertEquals(c, p.disproveSuggestion());

		// if player has no matching card
		ComputerPlayer p2 = new ComputerPlayer();

		board.makeSuggestion(new Card("iRrelevant", CardType.WEAPON), new Card("Irrelevant", CardType.ROOM),
				new Card("irrelevant", CardType.PERSON));

		assertNull(p2.disproveSuggestion());

		// if player has more than one matching card
		ComputerPlayer p3 = new ComputerPlayer();

		Card c2 = new Card("Den", CardType.ROOM);
		p3.giveCard(c);
		p3.giveCard(c2);

		board.makeSuggestion(c, c2, new Card("irrelevant", CardType.PERSON));

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
	
	/**
	 * Test that players create new suggestions properly - they do not suggest a card they have already seen
	 * and they are in the room that they suggest the murder was done in
	 */
	@Test
	public void testCreateSuggestion() {

		Board board = Board.getInstance();

		ComputerPlayer p = new ComputerPlayer();
		p.setLocation(4, 3); // should be in Game Room
		p.giveCard(new Card("Colonel Mustard", CardType.PERSON));
		p.giveCard(new Card("Knife", CardType.WEAPON));
		p.giveCard(new Card("Den", CardType.ROOM));

		p.giveSeenCard(new Card("Candlestick", CardType.WEAPON));
		p.giveSeenCard(new Card("Rope", CardType.WEAPON));
		p.giveSeenCard(new Card("Revolver", CardType.WEAPON));
		p.giveSeenCard(new Card("Wrench", CardType.WEAPON));

		p.giveSeenCard(new Card("Professor Plum", CardType.PERSON));
		p.giveSeenCard(new Card("Miss Scarlet", CardType.PERSON));
		p.giveSeenCard(new Card("Mr. Green", CardType.PERSON));
		p.giveSeenCard(new Card("Mrs. Peacock", CardType.PERSON));

		p.createSuggestion();

		Card[] suggested = board.getSuggestedCards();
		// doesn't choose from cards that it has
		assertNotEquals(new Card("Colonel Mustard", CardType.PERSON), suggested[0]);
		assertNotEquals(new Card("Knife", CardType.WEAPON), suggested[1]);
		assertNotEquals(new Card("Den", CardType.ROOM), suggested[2]);
		// has to be in the room
		assertEquals(p.getRoom().getInitial(), 'G');
		assertEquals(new Card("Game room", CardType.ROOM), suggested[2]);

		// one weapon not seen
		assertEquals(new Card("Poison", CardType.WEAPON), suggested[1]);

		// one person not seen
		assertEquals(new Card("Mrs. White", CardType.PERSON), suggested[0]);
	}
	
	/**
	 * Test that the board handles suggestions properly - that they return null when no player can disprove the suggestion or if only the
	 * suggesting player can, and that they return the proper disproving card when a player has a card that contradicts the suggestion
	 */
	@Test
	public void testHandleSuggestion() {
		Card[] answer = board.getChosenCards();
		
		// Suggestion no one can disprove returns null
		board.makeSuggestion(answer[0], answer[1], answer[2]);
		assertNull(board.handleSuggestions());
		
		// Suggestion only accusing player can disprove returns null
		Player[] players = board.getPeople();
		players[0].giveCard(answer[0]);
		assertNull(board.handleSuggestions());
		
		// Suggestion that two players can disprove, correct player (based on
		// starting with next player in list) returns answer
		players[1].giveCard(answer[1]);
		players[2].giveCard(answer[2]);
		assertEquals(board.handleSuggestions(), answer[1]);

	}
	
	/**
	 * Test that the board handles accusations correctly
	 * TODO what does this specifically test for?
	 */
	@Test
	public void testMakeAccusation() {
		Card[] answer = board.getChosenCards(); // correct answers

		// correct answer
		assertTrue(board.makeAccusation(answer[0], answer[1], answer[2]));

		// get a player that is not the chosen player
		Card person = null;
		for (Player p : board.getPeople()) {
			if (!new Card(p.getName(), CardType.PERSON).equals(answer[0])) {
				person = new Card(p.getName(), CardType.PERSON);
			}
		}
		assertFalse(board.makeAccusation(person, answer[1], answer[2]));

		// get a weapon that is not the chosen player
		String[] weapons = { "Candlestick", "Knife", "Rope", "Revolver", "Wrench", "Poison" };
		Card weapon = null;
		for (String w : weapons) {
			if (!new Card(w, CardType.WEAPON).equals(answer[1])) {
				weapon = new Card(w, CardType.WEAPON);
			}
		}
		assertFalse(board.makeAccusation(answer[0], weapon, answer[2]));

		// get a room that is not the chosen player
		String[] rooms = { "Game room", "Living room", "Office", "Bedroom", "Den", "Dining room", "Kitchen",
				"Greenhouse", "Theater" };
		Card room = null;
		for (String r : rooms) {
			if (!new Card(r, CardType.ROOM).equals(answer[2])) {
				room = new Card(r, CardType.ROOM);
			}
		}
		assertFalse(board.makeAccusation(answer[0], answer[1], room));

	}
	
	/**
	 * Test that player selects targets properly
	 */
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

		// test that if room was just visited, it may be chosen
		board.calcTargets(7, 8, 1);
		assertEquals(player.pickLocation(board.getTargets()), board.getCellAt(6, 8));
	}

}
