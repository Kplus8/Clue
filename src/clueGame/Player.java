package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;

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

	public Player() {
		super();
	}
	
	public Player(String playerName) {
		this.playerName = playerName;
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
