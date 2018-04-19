package clueGame;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 *
 */
public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, String color) {
		super(playerName, color);
	}

	/**
	 * Pass through list of targets, waits for player to select choice
	 * 
	 * @return selected cell
	 */

	public void makeMove() {
		Board.getInstance().setPlayerMakeMove(true);
	}

}
