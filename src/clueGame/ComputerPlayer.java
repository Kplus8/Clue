package clueGame;

import java.util.ArrayList;
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
	
	BoardCell justVisited;
	/**
	 * Picks a location to move to
	 * @param set
	 * @return BoardCell
	 */
	public BoardCell pickLocation(Set<BoardCell> set) {
		if(this.getRoom() != null) {
			justVisited = this.getRoom();
		}
		
		BoardCell[] options =  set.toArray(new BoardCell[0]);
		
		for(BoardCell s : set) {
			if(s.isRoom() && s != justVisited) {
				return s;
			}
		}
		Random random = new Random();
		return options[random.nextInt(options.length)];
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
