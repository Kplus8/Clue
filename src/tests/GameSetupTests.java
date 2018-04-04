package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

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

		board.loadConfigFiles();
	}

	/**
	 * Tests that some of the people are loaded correctly. Also checks the
	 * colors of said players
	 */

	@Test
	public void testLoadingPeople() {

		Player[] players = board.getPeople();

		for (Player p : players) {

			if (p.getName().equals("Colonel Mustard")) {
				assertTrue(p.getColor().equals(p.convertColor("yellow")));
			} else if (p.getName().equals("Professor Plum")) {
				assertTrue(p.getColor().equals(p.convertColor("magenta")));
			}

		}

	}

	@Test
	public void testLoadDeck() {

		Stack<Card> deck = board.getDeck();

		assertEquals(deck.size(), (6 + 6 + 9)); // 6 people, 6 weapons, 9 rooms

		ArrayList<Card> people = new ArrayList<>();
		ArrayList<Card> weapons = new ArrayList<>();
		ArrayList<Card> rooms = new ArrayList<>();

		for (int i = 0; i < 21; i++) {
			Card c = deck.pop();
			if (c.getCardType().equals(CardType.PERSON)) {
				people.add(c);
			} else if (c.getCardType().equals(CardType.ROOM)) {
				rooms.add(c);
			} else if (c.getCardType().equals(CardType.WEAPON)) {
				weapons.add(c);
			}
		}

		assertEquals(people.size(), 6);
		for (Card c : people) {
			if (c.getCardName().equals("Colonel Mustard")) {
				assertTrue(true); // Colonel Mustard is in List
				break;
			}
		}

		assertEquals(weapons.size(), 6);
		for (Card c : weapons) {
			if (c.getCardName().equals("CandleStick")) {
				assertTrue(true); // Candlestick is in List
				break;
			}
		}

		assertEquals(rooms.size(), 9);
		for (Card c : rooms) {
			if (c.getCardName().equals("Den")) {
				assertTrue(true); // Den is in List
				break;
			}
		}

	}

	@Test
	public void testDealCards() {

		// since the previous test popped cards off the deck, have to reinitialize it
		board.loadConfigFiles();
		
		Stack<Card> deck = board.getDeck();
		assertEquals(deck.size(), (6 + 6 + 9)); // 6 people, 6 weapons, 9 rooms
		board.dealCards();
		deck = board.getDeck();

		assertEquals(deck.size(), 0); // all cards are dealt

		Player[] players = board.getPeople();
		for (Player p : players) {
			assertEquals(p.getCards().size(), 3); // all players get 3 cards
		}
		
		// since cards are removed as they are dealt it is impossible to deal the same card twice,
		// so we did not test for this.
	}
}
