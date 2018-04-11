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

	@Test
	public void testHandleSuggestion() {

		ComputerPlayer p1 = new ComputerPlayer();
		ComputerPlayer p2 = new ComputerPlayer();
		ComputerPlayer p3 = new ComputerPlayer();
		ComputerPlayer p4 = new ComputerPlayer();
		ComputerPlayer p5 = new ComputerPlayer();
		Player hp = new Player();

		Card c1 = new Card("Colonel Mustard", CardType.PERSON);
		Card c2 = new Card("Den", CardType.ROOM);
		Card c3 = new Card("Knife", CardType.WEAPON);
		// Suggestion no one can disprove returns null
		board.makeSuggestion(c1, c2, c3);
		assertEquals(board.handleSuggestions(), null);
		// Suggestion only accusing player can disprove returns null
		p1.giveCard(c1);
		p1.giveCard(new Card("Irrelevant", CardType.WEAPON));
		p1.giveCard(new Card("Irrelevant", CardType.ROOM));
		assertEquals(board.handleSuggestions(), new Card("Colonel Mustard", CardType.PERSON));
		// Suggestion only human can disprove returns answer (i.e., card that
		// disproves suggestion)
		assertEquals(board.handleSuggestions(), new Card("Colonel Mustard", CardType.PERSON));
		// Suggestion only human can disprove, but human is accuser, returns
		// null
		assertEquals(board.handleSuggestions(), null);
		// Suggestion that two players can disprove, correct player (based on
		// starting with next player in list) returns answer
		p2.giveCard(c2);
		p1.giveCard(new Card("Irrelevant", CardType.WEAPON));
		p1.giveCard(new Card("Irrelevant", CardType.PERSON));
		assertEquals(board.handleSuggestions(), c1);

		// Suggestion that human and another player can disprove, other player
		// is next in list, ensure other player returns answer
		assertEquals(board.handleSuggestions(), c1);

	}

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
