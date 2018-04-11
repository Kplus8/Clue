package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Creates the Game Control GUI
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */

public class LowerGUI extends JPanel {

	private static final long serialVersionUID = 1L;

	public LowerGUI() {
		setLayout(new GridLayout(2, 2));

		// Upper half, with whose turn it is and buttons

		JPanel upper = new JPanel();
		upper.setLayout(new GridLayout(1, 2));
		JPanel turn = new JPanel();
		turn.add(new JLabel("Whose Turn?"));
		JTextField wTurn = new JTextField(20);
		wTurn.setEditable(false);
		turn.add(wTurn);
		upper.add(turn);
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.add(new JButton("Next Player"), BorderLayout.WEST);
		buttons.add(new JButton("Make an accusation"), BorderLayout.EAST);
		upper.add(buttons);
		add(upper);

		// lower half, 3 panels

		JPanel lower = new JPanel();
		lower.setLayout(new GridLayout(1, 3));

		// die panel

		JPanel die = new JPanel();
		die.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		JTextField roll = new JTextField(3);
		roll.setEditable(false);
		die.add(new JLabel("Roll"));
		die.add(roll);
		lower.add(die);

		// guess panel

		JPanel guess = new JPanel();
		guess.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		guess.add(new JLabel("Guess"));
		JTextField sGuess = new JTextField(15);
		sGuess.setEditable(false);
		guess.add(sGuess);
		lower.add(guess);

		// response panel
		
		JPanel guessResponse = new JPanel();
		guessResponse.setBorder(new TitledBorder(new EtchedBorder(),
				"Guess Result"));
		guessResponse.add(new JLabel("Response"));
		JTextField response = new JTextField(15);
		response.setEditable(false);
		guessResponse.add(response);
		lower.add(guessResponse);

		add(lower);
	}

}