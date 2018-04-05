package clueGame;

import java.util.HashSet;

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
	 * @param targets
	 * @return BoardCell
	 */
	public BoardCell pickLocation(HashSet<BoardCell> targets) {
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
