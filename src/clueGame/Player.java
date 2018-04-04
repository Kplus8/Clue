package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 *
 */

public class Player {

	private String playerName;
	private int row, column;
	private Color color;
	private ArrayList<Card> cards;
	private ArrayList<Card> seenCards;

	public Player() {
		super();
	}
	
	public Player(String playerName) {
		this.playerName = playerName;
	}

	public Color getColor() {
		return color;
	}
	
	public String getName() {
		return playerName;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
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
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
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

}
