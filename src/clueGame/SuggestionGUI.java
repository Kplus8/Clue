package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.*;

public class SuggestionGUI extends JDialog implements ActionListener {

	private JComboBox<String> cPerson, cWeapon;
	private JLabel room;

	public SuggestionGUI() {

		setModal(true);
		setLocationRelativeTo(null);
		Board board = Board.getInstance();
		setLayout(new GridLayout(4, 2));
		setTitle("Make a Guess");
		setSize(300, 300);

		add(new JLabel("Your room"));

		// get room
		try {
			room = new JLabel(board.getRoom(board.getActivePlayer().getRoom().getInitial()));
			add(room);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

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
			Card p = new Card(cPerson.getSelectedItem().toString(), CardType.PERSON);
			Card w = new Card(cWeapon.getSelectedItem().toString(), CardType.WEAPON);
			Card r = new Card(room.getText(), CardType.ROOM);
			Board.getInstance().makeSuggestion(p, w, r);
			LowerGUI.getInstance().setGuessText(p.getCardName() + ", " + r.getCardName() + ", " + w.getCardName());
			this.dispose();
		} else {
			this.dispose();
		}

	}

}
