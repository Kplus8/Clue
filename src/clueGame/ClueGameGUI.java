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

	public ClueGameGUI() {

		//setLayout(new GridLayout(1, 1));
		
		// My Cards panel
		
		JPanel rightSide = new JPanel();
		rightSide.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		rightSide.setLayout(new GridLayout(3, 1));
		
		// people
		
		JPanel people = new JPanel();
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		String[] p = {""};
		JList<String> lPeople = new JList<>(p);
		lPeople.setPreferredSize(new Dimension(100, 100));
		people.add(lPeople);
		rightSide.add(people);

		// rooms
		
		JPanel rooms = new JPanel();
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		String[] r = {""};
		JList<String> lRooms = new JList<>(r);
		lRooms.setPreferredSize(new Dimension(100, 100));
		rooms.add(lRooms);
		rightSide.add(rooms);
		
		// weapons
		
		JPanel weapons = new JPanel();
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		String[] w = {""};
		JList<String> lWeapons = new JList<>(w);
		lWeapons.setPreferredSize(new Dimension(100, 100));
		weapons.add(lWeapons);
		rightSide.add(weapons);
		
		add(rightSide);
	}

}
