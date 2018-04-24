package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * A player which chooses moves algorithmically as opposed to choosing according
 * to human input
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class ComputerPlayer extends Player {

	private Solution lastCombo;

	BoardCell justVisited;
	private boolean readyAccusation = false;

	public ComputerPlayer(String playerName, String color) {
		super(playerName, color);
	}

	public ComputerPlayer() {
		super();
	}

	public boolean getReadyAccusation() {
		return super.canAccuse;
	}

	/**
	 * Picks a location to move to
	 * 
	 * @param set
	 * @return BoardCell
	 */
	public BoardCell pickLocation(Set<BoardCell> set) {
		if (this.getRoom() != null) {
			justVisited = this.getRoom();
		}

		BoardCell[] options = set.toArray(new BoardCell[0]);

		for (BoardCell s : set) {
			if (s.isRoom() && s != justVisited) {
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
		Board board = Board.getInstance();

		if (board.makeAccusation(lastCombo.getPersonCard(), lastCombo.getWeaponCard(), lastCombo.getRoomCard())) {

			JOptionPane.showMessageDialog(null,
					this.getName() + " has won! The answer was " + lastCombo.getPerson() + " in the "
							+ lastCombo.getRoom() + " with the " + lastCombo.getWeapon() + ".",
					"Clue", JOptionPane.INFORMATION_MESSAGE);
			Runtime.getRuntime().exit(0);

		} else {
			JOptionPane.showMessageDialog(null,
					this.getName() + " guessed incorrectly, their guess was " + lastCombo.getPerson() + " in the "
							+ lastCombo.getRoom() + " with the " + lastCombo.getWeapon() + ".",
					"Clue", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Creates a suggestion
	 */
	public void createSuggestion() {
		Board board = Board.getInstance();

		// separate all cards into card types

		ArrayList<Card> players = new ArrayList<>();
		ArrayList<Card> weapons = new ArrayList<>();
		ArrayList<Card> rooms = new ArrayList<>();

		Card player = null, weapon = null, room = null;

		try {
			room = new Card(board.getRoom(this.getRoom().getInitial()), CardType.ROOM);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (Card c : super.getCards()) {
			if (c.getCardType() == CardType.PERSON) {
				players.add(c);
			} else if (c.getCardType() == CardType.WEAPON) {
				weapons.add(c);
			} else if (c.getCardType() == CardType.ROOM) {
				rooms.add(c);
			}
		}

		for (Card c : super.getSeenCards()) {
			if (c.getCardType() == CardType.PERSON) {
				players.add(c);
			} else if (c.getCardType() == CardType.WEAPON) {
				weapons.add(c);
			} else if (c.getCardType() == CardType.ROOM) {
				rooms.add(c);
			}
		}

		Random r = new Random();

		// choose a player

		if (players.size() - board.getPeople().length == 0) {
			player = players.get(r.nextInt(players.size()));
		} else {
			boolean cont = true;
			while (cont) {
				player = new Card(board.getPeople()[r.nextInt(board.getPeople().length)].getName(), CardType.PERSON);
				boolean cont2 = true;
				for (Card c : players) {
					if (c.equals(player)) {
						cont2 = false;
						break;
					}
				}
				if (cont2)
					cont = false;
			}
		}

		// choose a weapon

		if (weapons.size() - board.getWeapons().length == 0) {
			weapon = weapons.get(r.nextInt(weapons.size()));
		} else {
			boolean cont = true;
			while (cont) {
				weapon = board.getWeapons()[r.nextInt(board.getWeapons().length)];
				boolean cont2 = true;
				for (Card c : weapons) {
					if (c.equals(weapon)) {
						cont2 = false;
						break;
					}
				}
				if (cont2)
					cont = false;
			}
		}

		LowerGUI.getInstance()
				.setGuessText(player.getCardName() + ", " + room.getCardName() + ", " + weapon.getCardName());

		lastCombo = new Solution(player, room, weapon);
		board.makeSuggestion(player, weapon, room);

	}
}
