package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Creates the Clue Game GUI
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */

public class ClueGameGUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates ClueGameGUI
	 */
	
	public ClueGameGUI() {

		// get player's cards
		
		Board board = Board.getInstance();
		Player[] players = board.getPeople();
		HumanPlayer hp = null;
		for (Player pl : players) {
			if (pl.getName().equals("Miss Scarlet")) {
				hp = (HumanPlayer) pl;
				break;
			}
		}
		int numP = 0, numR = 0, numW = 0;
		for (Card c : hp.getCards()) {
			if (c.getCardType()==CardType.PERSON) {
				numP++;
			} else if (c.getCardType()==CardType.WEAPON) {
				numW++;
			} else if (c.getCardType()==CardType.ROOM) {
				numR++;
			}
		}
		String[] p = new String[numP];
		String[] r = new String[numR];
		String[] w = new String[numW];
		int iP = 0, iR = 0, iW = 0;
		for (Card c : hp.getCards()) {
			if (c.getCardType()==CardType.PERSON) {
				p[iP] = c.getCardName();
				iP++;
			} else if (c.getCardType()==CardType.WEAPON) {
				w[iW] = c.getCardName();
				iW++;
			} else if (c.getCardType()==CardType.ROOM) {
				r[iR] = c.getCardName();
				iR++;
			}
		}
		
		// My Cards panel
		
		JPanel rightSide = new JPanel();
		rightSide.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		rightSide.setLayout(new GridLayout(3, 1));
		
		// people
		
		JPanel people = new JPanel();
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JList<String> lPeople = new JList<>(p);
		lPeople.setPreferredSize(new Dimension(100, 100));
		people.add(lPeople);
		rightSide.add(people);

		// rooms
		
		JPanel rooms = new JPanel();
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JList<String> lRooms = new JList<>(r);
		lRooms.setPreferredSize(new Dimension(100, 100));
		rooms.add(lRooms);
		rightSide.add(rooms);
		
		// weapons
		
		JPanel weapons = new JPanel();
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		JList<String> lWeapons = new JList<>(w);
		lWeapons.setPreferredSize(new Dimension(100, 100));
		weapons.add(lWeapons);
		rightSide.add(weapons);
		
		add(rightSide);
	}

}
