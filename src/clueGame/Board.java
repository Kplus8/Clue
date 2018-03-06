package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 *
 */

public class Board {

	public static final int MAX_BOARD_SIZE = 50;

	private int numRows;
	private int numCols;
	private static BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> visited;

	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * Sets the legend and layout files
	 * 
	 * @param layout
	 * @param legend
	 */

	public void setConfigFiles(String layout, String legend) {

		boardConfigFile = layout;
		roomConfigFile = legend;

	}

	/**
	 * Calculates the number of cols and rows, reads in the legend file, and
	 * reads in the map file.
	 */

	public void initialize() {

		// get numRows and numCols
		try {
			readRowsCols();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		legend = new HashMap<Character, String>();
		board = new BoardCell[numRows][numCols];

		// read in legend file
		try {
			loadRoomConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}

		// read in map file
		try {
			loadBoardConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reads in legend file, populating Legend Map
	 * 
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */

	public void loadRoomConfig() throws FileNotFoundException,
			BadConfigFormatException {

		Scanner sc = new Scanner(new File(roomConfigFile));

		while (sc.hasNextLine()) {

			String line = sc.nextLine();
			String[] parts = line.split(", ");
			if (!(parts[2] == "Card" || parts[2] == "Other")) {
				sc.close();
				throw new BadConfigFormatException(
						"Unrecognized type in Legend file: " + roomConfigFile);
			}
			legend.put(line.charAt(0), parts[1]);

		}

		sc.close();

	}

	/**
	 * Reads in Map file, populating board.
	 * 
	 * @throws FileNotFoundException
	 */

	public void loadBoardConfig() throws FileNotFoundException,
			BadConfigFormatException {

		Scanner sc = new Scanner(new File(boardConfigFile));

		int row = 0;

		while (sc.hasNextLine()) {

			String line = sc.nextLine();
			String[] parts = line.split(",");

			for (int column = 0; column < parts.length; column++) {

				if (parts.length != numCols) {
					sc.close();
					throw new BadConfigFormatException(
							"Mismatched column length. " + boardConfigFile);
				}

				if (legend.keySet().contains(parts[column].charAt(0))) {
					sc.close();
					throw new BadConfigFormatException("Unrecognized initial. "
							+ boardConfigFile);
				}

				if (parts[column].length() == 1) {
					board[row][column] = new BoardCell(row, column,
							parts[column].charAt(0), DoorDirection.NONE);
				} else {
					if (parts[column].substring(1).equals("U")) {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.UP);
					} else if (parts[column].substring(1).equals("D")) {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.DOWN);
					} else if (parts[column].substring(1).equals("L")) {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.LEFT);
					} else if (parts[column].substring(1).equals("R")) {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.RIGHT);
					} else {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.NONE);
					}

				}

			}

			row++;

		}

		sc.close();

		if (row != numRows) {
			throw new BadConfigFormatException("Mismatched column length.");
		}

	}

	/**
	 * Calculated number of cols and rows.
	 * 
	 * @throws FileNotFoundException
	 */

	public void readRowsCols() throws FileNotFoundException {

		numCols = 0;
		numRows = 0;

		Scanner sc = new Scanner(new File(boardConfigFile));
		while (sc.hasNextLine()) {

			String[] parts = sc.nextLine().split(",");
			numRows++;
			numCols = parts.length;

		}

		sc.close();

	}

	/**
	 * Returns legend Map
	 * 
	 * @return
	 */

	public Map<Character, String> getLegend() {

		return legend;
	}

	/**
	 * Returns number of rows
	 * 
	 * @return
	 */

	public int getNumRows() {
		return numRows;
	}

	/**
	 * Returns number of cols
	 * 
	 * @return
	 */

	public int getNumCols() {
		return numCols;
	}

	/**
	 * Returns the cell at pos (x, y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	public BoardCell getCellAt(int x, int y) {

		return board[x][y];

	}

}
