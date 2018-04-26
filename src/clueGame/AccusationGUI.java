package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.*;

public class AccusationGUI extends JDialog implements ActionListener {

	private JComboBox<String> cRoom, cPerson, cWeapon;

	public AccusationGUI() {

		setModal(true);
		setLocationRelativeTo(null);
		Board board = Board.getInstance();
		setLayout(new GridLayout(4, 2));
		setTitle("Make an Accusation");
		setSize(300, 300);

		add(new JLabel("Rooms"));

		// get room
		String[] rooms = new String[board.getRooms().length];
		for (int i = 0; i < board.getRooms().length; i++) {
			rooms[i] = board.getRooms()[i].getCardName();
		}
		cRoom = new JComboBox<String>(rooms);
		add(cRoom);

		add(new JLabel("Person"));
		// get people
		String[] people = new String[board.getPeople().length];
		for (int i = 0; i < board.getPeople().length; i++) {
			people[i] = board.getPeople()[i].getName();
		}
		cPerson = new JComboBox<String>(people);
		add(cPerson);

		add(new JLabel("Weapon"));
		// get weapons
		String[] weapons = new String[board.getWeapons().length];
		for (int i = 0; i < board.getWeapons().length; i++) {
			weapons[i] = board.getWeapons()[i].getCardName();
		}
		cWeapon = new JComboBox<String>(weapons);
		add(cWeapon);

		// buttons
		JButton submit = new JButton("Submit");
		submit.setActionCommand("submit");
		submit.addActionListener(this);
		add(submit);

		JButton cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		add(cancel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("submit")) {
			// submit
			Card p = new Card(cPerson.getSelectedItem().toString(),
					CardType.PERSON);
			Card w = new Card(cWeapon.getSelectedItem().toString(),
					CardType.WEAPON);
			Card r = new Card(cRoom.getSelectedItem().toString(), CardType.ROOM);
			if (Board.getInstance().makeAccusation(p, r, w)) {
				// correct
				JOptionPane.showMessageDialog(null,
						"Correct, you win! The answer was "
								+ cPerson.getSelectedItem().toString()
								+ " in the "
								+ cRoom.getSelectedItem().toString()
								+ " with the "
								+ cWeapon.getSelectedItem().toString() + ".",
						"Clue", JOptionPane.INFORMATION_MESSAGE);
				Runtime.getRuntime().exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "Sorry, that's not right.",
						"Clue", JOptionPane.INFORMATION_MESSAGE);
				Board board = Board.getInstance();
				board.passTurn();
				// enable button
				LowerGUI.getInstance().setNPEnabled();
				// repaint graphics
				board.paintComponent(board.getGraphics());
				board.setPlayerMakeMove(false);
			}
			this.dispose();
		} else {
			this.dispose();
		}

	}

}
