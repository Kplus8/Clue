package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Represents a player and information about them
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class Player {
	private String playerName;
	private int row, column;
	private Color color;
	private ArrayList<Card> cards;
	private ArrayList<Card> seenCards;

	public Player() {
		super(); // I think this is implicit for children of Object (and in
					// general)?
	}
	
	public Player(String playerName, String color) {
		this.playerName = playerName;
		this.color = convertColor(color);

		cards = new ArrayList<>();
		seenCards = new ArrayList<>();
	}

	/**
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	public void setLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return playerName;
	}

	/**
	 * @return cards
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	/**
	 * @return seenCards
	 */
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	/**
	 * Adds the specified card to this player's cards
	 * 
	 * @param card
	 */
	public void giveCard(Card card) {
		cards.add(card);
	}
	
	/**
	 * Adds the specified card to this player's seen cards
	 * 
	 * @param card
	 */
	public void giveSeenCard(Card card) {
		seenCards.add(card);
	}

	/**
	 * Converts a string to a color
	 * 
	 * @param strColor
	 * @return Color
	 */
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor);
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}

	/**
	 * Provides a card that disproves the suggestion
	 *
	 * @return Card
	 */
	public Card disproveSuggestion() {
		return null;
	}

	public BoardCell getRoom() {
		Board board = Board.getInstance();
		BoardCell cell = board.getCellAt(row, column);

		if (cell.getInitial() != 'W') {
			return cell;
		} else {
			return null;
		}
	}

}
