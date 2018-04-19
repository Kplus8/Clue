package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

public class LowerGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static LowerGUI theInstance = new LowerGUI();

	private JTextField response, sGuess, wTurn, roll;
	private JButton np;

	public static LowerGUI getInstance() {
		return theInstance;
	}

	/**
	 * Creates GameControlGUI
	 */

	private LowerGUI() {
		setLayout(new GridLayout(2, 2));

		// Upper half, with whose turn it is and buttons

		JPanel upper = new JPanel();
		upper.setLayout(new GridLayout(1, 2));
		JPanel turn = new JPanel();
		turn.add(new JLabel("Whose Turn?"));
		wTurn = new JTextField(20);
		wTurn.setEditable(false);
		turn.add(wTurn);
		upper.add(turn);
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		np = new JButton("Next Player");
		np.setActionCommand("next player");
		np.addActionListener(this);
		buttons.add(np, BorderLayout.WEST);
		JButton ma = new JButton("Make an accusation");
		ma.setActionCommand("accusation");
		ma.addActionListener(this);
		buttons.add(ma, BorderLayout.EAST);
		upper.add(buttons);
		add(upper);

		// lower half, 3 panels

		JPanel lower = new JPanel();
		lower.setLayout(new GridLayout(1, 3));

		// die panel

		JPanel die = new JPanel();
		/* die.setPreferredSize(new Dimension(50, 50)); */
		die.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		roll = new JTextField(3);
		roll.setEditable(false);
		die.add(new JLabel("Roll"));
		die.add(roll);
		lower.add(die);

		// guess panel

		JPanel guess = new JPanel();
		guess.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		guess.add(new JLabel("Guess"));
		sGuess = new JTextField(30);
		sGuess.setEditable(false);
		guess.add(sGuess);
		lower.add(guess);

		// response panel

		JPanel guessResponse = new JPanel();
		guessResponse.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		guessResponse.add(new JLabel("Response"));
		response = new JTextField(15);
		response.setEditable(false);
		guessResponse.add(response);
		lower.add(guessResponse);

		add(lower);
	}

	public void setNPEnabled() {
		np.setEnabled(true);
	}

	public void setGuessText(String text) {
		sGuess.setText(text);
	}

	public void setResponseText(String text) {
		response.setText(text);
	}

	/**
	 * Action Listener
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("next player")) {
			// next player

			// roll die
			Random rand = new Random();
			roll.setText((rand.nextInt(6) + 1) + "");

			// get players

			Board board = Board.getInstance();
			Player ap = board.getActivePlayer();
			wTurn.setText(ap.getName());

			if (board.getPeople()[0].equals(ap)) { // human player

				HumanPlayer hp = (HumanPlayer) ap;
				board.calcTargets(hp.getRow(), hp.getColumn(), Integer.parseInt(roll.getText()));
				board.paintTargets(board.getGraphics());
				np.setEnabled(false); // disables next player button
				// player makes choice
				hp.makeMove(); // waits for player
			} else { // computer player

				ComputerPlayer cp = (ComputerPlayer) ap;

				if (cp.getReadyAccusation()) { // make accusation

				} else { // move player
					board.calcTargets(cp.getRow(), cp.getColumn(), Integer.parseInt(roll.getText()));
					BoardCell cell = cp.pickLocation(board.getTargets());
					cp.setLocation(cell.getRow(), cell.getColumn());
					// if entered room, make guess
					if (cell.isRoom()) {
						cp.createSuggestion();
					}
				}
				// increment active player
				board.passTurn();
				// redraw graphics
				board.paintComponent(board.getGraphics());
			}
		} else if (e.getActionCommand().equals("accusation")) {
			// make an accusation

		}

	}

}
