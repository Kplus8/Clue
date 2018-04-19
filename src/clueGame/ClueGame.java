package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class ClueGame extends JFrame implements ActionListener {

	/**
	 * Main, creates GUI
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		// load Board
		Board board = Board.getInstance();
		board.setConfigFiles("data" + File.separatorChar + "Map.csv", "data" + File.separatorChar + "ClueRooms.txt");
		board.initialize();
		board.loadConfigFiles();
		board.dealCards();
		
		// load GUI
		ClueGame game = new ClueGame();
		game.createGUI();
		
		// display splash
		JOptionPane.showMessageDialog(null, "You are Miss Scarlet. Press Next Player to begin.", "Clue", JOptionPane.INFORMATION_MESSAGE);
		

	}

	/**
	 * Creates GUI
	 */
	
	public void createGUI() {
		setSize(800, 800);
		add(LowerGUI.getInstance(), BorderLayout.SOUTH);
		add(new ClueGameGUI(), BorderLayout.EAST);

		// menu bar
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem detective = new JMenuItem("View Detective Notes");
		detective.addActionListener(this);
		detective.setActionCommand("detective pikachu");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		exit.setActionCommand("exit");
		file.add(detective);
		file.add(exit);
		menu.add(file);

		add(menu, BorderLayout.NORTH);

		Board board = Board.getInstance();
		board.setConfigFiles("data" + File.separatorChar + "Map.csv", "data" + File.separatorChar + "ClueRooms.txt");
		board.initialize();
		add(board, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Action Listener
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("exit")) {
			this.dispose();
		} else if (e.getActionCommand().equals("detective pikachu")) {
			// show detective notes
			DetectiveNotes d = new DetectiveNotes();
			d.setVisible(true);
		}

	}

}
