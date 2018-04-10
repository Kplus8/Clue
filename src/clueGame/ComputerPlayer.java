package clueGame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A player which chooses moves algorithmically as opposed to choosing according to human input
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class ComputerPlayer extends Player{
	/**
	 * Picks a location to move to
	 * @param set
	 * @return BoardCell
	 */
	public BoardCell pickLocation(Set<BoardCell> set) {
		String[] rooms = { "Game room", "Living room", "Office", "Bedroom", "Den", "Dining room", "Kitchen",
				"Greenhouse", "Theater" };
		Set<BoardCell> options = new HashSet<>();
		Random die = new Random();
		
		
		
		return null;
		
	}
	
	/**
	 * Makes an accusation
	 */
	public void makeAccusations() {
		return;
	}
	
	/**
	 * Creates a suggestion
	 */
	public void createSuggestion() {
		return;
	}
}
