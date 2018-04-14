package clueGame;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DetectiveNotes() {

		setLayout(new GridLayout(3, 2));
		Board board = Board.getInstance();

		// get people
		JPanel people = new JPanel();
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		// people.setLayout(new GridLayout());
		String[] lPeople = new String[board.getPeople().length];
		for (int i = 0; i < board.getPeople().length; i++) {
			Player p = board.getPeople()[i];
			JCheckBox check = new JCheckBox(p.getName());
			people.add(check);
			lPeople[i] = p.getName();
		}

		// person guess
		JPanel pGuess = new JPanel();
		pGuess.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		pGuess.add(new JComboBox<String>(lPeople));

		// get rooms
		JPanel rooms = new JPanel();
		String[] lRooms = new String[board.getRooms().length];
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		for (int i = 0; i < board.getRooms().length; i++) {
			Card r = board.getRooms()[i];
			JCheckBox check = new JCheckBox(r.getCardName());
			rooms.add(check);
			lRooms[i] = r.getCardName();
		}

		// room guess
		JPanel rGuess = new JPanel();
		rGuess.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		rGuess.add(new JComboBox<String>(lRooms));

		// get weapons
		JPanel weapons = new JPanel();
		String[] lWeapons = new String[board.getWeapons().length];
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		for (int i = 0; i < board.getWeapons().length; i++) {
			Card w = board.getWeapons()[i];
			JCheckBox check = new JCheckBox(w.getCardName());
			weapons.add(check);
			lWeapons[i] = w.getCardName();
		}

		// weapon guess
		JPanel wGuess = new JPanel();
		wGuess.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		wGuess.add(new JComboBox<String>(lWeapons));

		setSize(450, 500);
		setTitle("Detective Notes");
		add(people);
		add(pGuess);
		add(rooms);
		add(rGuess);
		add(weapons);
		add(wGuess);
	}

}
